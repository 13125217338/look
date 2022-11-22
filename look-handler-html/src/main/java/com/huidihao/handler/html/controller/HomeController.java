package com.huidihao.handler.html.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @作者 ChengShi
 * @日期 2022-11-22 10:04:21
 * @版本 1.0
 * @描述 页面控制
 */
@Controller
public class HomeController {
	
	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("msg", "测试");
		return "index";
	}
}
