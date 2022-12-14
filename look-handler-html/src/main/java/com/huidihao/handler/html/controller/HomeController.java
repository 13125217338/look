package com.huidihao.handler.html.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huidihao.handler.api.dto.AudioMsgDto;
import com.huidihao.handler.api.dto.QuestionDto;
import com.huidihao.handler.api.open.AudioMsgApi;
import com.huidihao.handler.api.open.QuestionApi;

/**
 * @作者 ChengShi
 * @日期 2022-11-22 10:04:21
 * @版本 1.0
 * @描述 页面控制
 */
@Controller
public class HomeController {
	@Autowired
	private AudioMsgApi audioMsgApi;
	@Autowired
	private QuestionApi questionApi;
	
	@GetMapping("/main")
	public String main(Model model) {
		return "main";
	}
	
	@GetMapping("/study")
	public String study(@RequestParam("type") int type, Model model) {
		model.addAttribute("type", type);
		return "study";
	}
	
	@GetMapping("/read")
	public String read(@RequestParam("place") String place, Model model) {
		AudioMsgDto audioMsgDto = new AudioMsgDto();
		audioMsgDto.setPlace(place).setPageNum(1).setPageSize((int) Short.MAX_VALUE);
		model.addAttribute("datas", audioMsgApi.list(audioMsgDto));
		model.addAttribute("place", place);
		return "read";
	}
	
	@GetMapping("/upload")
	public String upload(Model model) {
		return "upload";
	}
	
	@GetMapping("/bill")
	public String bill(Model model) {
		return "bill";
	}
	
	@GetMapping("/question")
	public String question(Model model) {
		QuestionDto questionDto = new QuestionDto();
		questionDto.setPageNum(1).setPageSize((int) Short.MAX_VALUE);
		/* 分页查询 */
		model.addAttribute("datas", questionApi.list(questionDto));
		return "question";
	}
}
