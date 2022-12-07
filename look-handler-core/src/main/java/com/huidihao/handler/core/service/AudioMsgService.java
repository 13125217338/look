package com.huidihao.handler.core.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.city.common.api.dto.Condition;
import org.city.common.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.huidihao.handler.api.dto.AudioMsgDto;
import com.huidihao.handler.api.open.AudioMsgApi;
import com.huidihao.handler.core.entity.AudioMsgEntity;
import com.huidihao.handler.core.handler.AudioHandler;

/**
 * @作者 ChengShi
 * @日期 2022-11-23 17:55:09
 * @版本 1.0
 * @描述 语音消息服务
 */
@Service
public class AudioMsgService extends AbstractService<AudioMsgDto, AudioMsgEntity> implements AudioMsgApi{
	@Autowired
	private AudioHandler audioHandler;

	@Override
	@Transactional
	public void push(AudioMsgDto audioMsgDto) throws Exception {
		/* 有语音才添加 */
		if (audioMsgDto.getFile() !=  null) {
			File outFile = new File("audio");
			if (!outFile.exists()) {outFile.mkdirs();}
			outFile = new File(outFile, audioMsgDto.getFile().getOriginalFilename());
			
			/* 保存本地并转成MP3格式 */
			try(InputStream in = audioMsgDto.getFile().getInputStream(); OutputStream out = new FileOutputStream(outFile)) {
				in.transferTo(out);
				File coverFile = new File(outFile.getAbsolutePath() + ".mp3");
				audioHandler.conver(outFile, coverFile);
				/* 语音数据 */
				audioMsgDto.setAudioName(coverFile.getName());
				audioMsgDto.setAudioPath(coverFile.getAbsolutePath());
			} finally {outFile.delete();}
			
		}
		/* 添加录音文件 */
		Assert.isTrue(add(audioMsgDto), String.format("用户%s未添加语音数据！", audioMsgDto.getName()));
	}
	
	@Override
	public void getAudio(HttpServletResponse response, long id) throws IOException {
		String audioPath = findOne(new Condition("id", id)).getAudioPath();
		Assert.notNull(audioPath, String.format("录音[%d]对应语音不存在！", id));
		
		/* 读取语音写出 */
		File file = new File(audioPath);
		response.setContentType("audio/mpeg");
		try(InputStream in = new FileInputStream(file)) {in.transferTo(response.getOutputStream());}
	}

	@Override
	public List<AudioMsgDto> list(AudioMsgDto audioMsgDto) {
		return findAll(new Condition("place", audioMsgDto.getPlace()).limit(audioMsgDto));
	}
}
