package com.huidihao.handler.core.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.city.common.api.dto.Condition;
import org.city.common.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.huidihao.handler.api.dto.AudioMsgDto;
import com.huidihao.handler.api.dto.WxConfigDto;
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
	/* 语音下载地址 */
	private final String AUDIO_URL = "https://file.api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
	@Autowired
	private RestTemplate restTemplate;

	@Override
	@Transactional
	public void push(AudioMsgDto audioMsgDto) throws Exception {
		/* 有语音才添加 */
		if (audioMsgDto.getFile() !=  null) {
			File outFile = getOutFile(audioMsgDto.getFile().getOriginalFilename());
			/* 保存本地并转成MP3格式 */
			try(InputStream in = audioMsgDto.getFile().getInputStream(); OutputStream out = new FileOutputStream(outFile)) {
				setFileInfo(audioMsgDto, outFile, in, out);
			} finally {outFile.delete();}
		
		/* 从微信服务器获取 */
		} else if(audioMsgDto.getAudioId() != null) {
			restTemplate.execute(String.format(AUDIO_URL, audioHandler.getToken(), audioMsgDto.getAudioId()), HttpMethod.GET,
				   request -> request.getHeaders().setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM)),
				   response -> {
					 File outFile = getOutFile(audioMsgDto.getAudioId());
					 /* 保存本地并转成MP3格式 */
					 try(OutputStream out = new FileOutputStream(outFile)) {
						 try {setFileInfo(audioMsgDto, outFile, response.getBody(), out);}
						 catch (Exception e) {throw new RuntimeException(e);}
					 } finally {outFile.delete();}
					 return null;
				});
		}
		/* 添加录音文件 */
		Assert.isTrue(add(audioMsgDto), String.format("用户%s未添加语音数据！", audioMsgDto.getName()));
	}
	/* 保存本地并转成MP3格式 */
	private void setFileInfo(AudioMsgDto audioMsgDto, File outFile, InputStream in, OutputStream out)
			throws IOException, Exception {
		in.transferTo(out);
		File coverFile = new File(outFile.getAbsolutePath() + ".mp3");
		audioHandler.conver(outFile, coverFile);
		/* 语音数据 */
		audioMsgDto.setAudioName(coverFile.getName());
		audioMsgDto.setAudioPath(coverFile.getAbsolutePath());
	}
	/* 获取输出流 */
	private File getOutFile(String fileName) throws FileNotFoundException {
		File outFile = new File("audio");
		if (!outFile.exists()) {outFile.mkdirs();}
		return new File(outFile, fileName);
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
	
	@Override
	public WxConfigDto getConfig(String url) {
		return audioHandler.getConfig(url);
	}
}
