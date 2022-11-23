package com.huidihao.handler.html.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.huidihao.handler.api.open.AudioMsgApi;

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
	
	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("audios", audioMsgApi.list());
		return "index";
	}
}
