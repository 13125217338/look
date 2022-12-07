package com.huidihao.handler.api.open;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.huidihao.handler.api.dto.AudioMsgDto;

/**
 * @作者 ChengShi
 * @日期 2022-11-23 19:00:28
 * @版本 1.0
 * @描述 录音消息
 */
public interface AudioMsgApi {

	/**
	 * @描述 推送语音数据
	 * @param audioMsgDto 录音数据
	 * @throws Exception
	 */
	public void push(AudioMsgDto audioMsgDto) throws Exception;
	
	/**
	 * @描述 列出语音数据列表
	 * @param audioMsgDto 语音数据
	 * @return 语音数据列表
	 */
	public List<AudioMsgDto> list(AudioMsgDto audioMsgDto);
	
	/**
	 * @描述 获取语音数据
	 * @param response 响应数据
	 * @param id 语音ID
	 * @throws IOException
	 */
	public void getAudio(HttpServletResponse response, long id) throws IOException;
}
