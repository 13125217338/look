package com.huidihao.handler.core.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.city.common.api.dto.Condition;
import org.city.common.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.huidihao.handler.api.dto.AudioMsgDto;
import com.huidihao.handler.api.open.AudioMsgApi;
import com.huidihao.handler.core.entity.AudioMsgEntity;

/**
 * @作者 ChengShi
 * @日期 2022-11-23 17:55:09
 * @版本 1.0
 * @描述 语音消息服务
 */
@Service
public class AudioMsgService extends AbstractService<AudioMsgDto, AudioMsgEntity> implements AudioMsgApi{

	@Override
	@Transactional
	public void push(AudioMsgDto audioMsgDto) throws IOException {
		/* 添加录音文件 */
		Assert.isTrue(add(audioMsgDto), String.format("用户%s未添加语音数据！", audioMsgDto.getName()));
		/* 有语音才添加 */
		if (audioMsgDto.getFile() !=  null) {
			File outFile = new File("audio");
			if (!outFile.exists()) {outFile.mkdirs();}
			outFile = new File(outFile, UUID.randomUUID() + audioMsgDto.getFile().getOriginalFilename());
			
			/* 语音数据 */
			audioMsgDto.setAudioName(outFile.getName());
			audioMsgDto.setAudioPath(outFile.getAbsolutePath());
			try(InputStream in = audioMsgDto.getFile().getInputStream(); OutputStream out = new FileOutputStream(outFile)) {in.transferTo(out);}
		}
	}
	
	@Override
	public void getAudio(HttpServletResponse response, long id) throws IOException {
		response.setContentType("audio/ogg; codecs=opus");
		String audioPath = findOne(new Condition("id", id)).getAudioPath();
		Assert.notNull(audioPath, String.format("录音[%d]对应语音不存在！", id));
		
		/* 读取语音写出 */
		try(InputStream in = new FileInputStream(audioPath)) {in.transferTo(response.getOutputStream());}
	}

	@Override
	public List<AudioMsgDto> list(AudioMsgDto audioMsgDto) {
		return findAll(new Condition("place", audioMsgDto.getPlace()).limit(audioMsgDto));
	}
}
