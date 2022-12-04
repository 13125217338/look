package com.huidihao.handler.core.controller;

import javax.servlet.http.HttpServletResponse;

import org.city.common.api.dto.Response;
import org.city.common.core.controller.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huidihao.handler.api.dto.AudioMsgDto;
import com.huidihao.handler.core.service.AudioMsgService;

/**
 * @作者 ChengShi
 * @日期 2022-11-23 17:52:06
 * @版本 1.0
 * @描述 语音消息处理
 */
@Controller
@RequestMapping("/data/handler/audio")
public class AudioMsgController extends AbstractController<AudioMsgService> {

	@ResponseBody
	@PostMapping("/push")
	public Response push(@Validated AudioMsgDto audioMsgDto) throws Exception {
		return okv(s -> s.push(audioMsgDto));
	}
	
	@GetMapping("/getAudio")
	public void getAudio(HttpServletResponse response, @RequestParam("id") long id) throws Exception {
		service.getAudio(response, id);
	}
}
