package com.huidihao.handler.core.controller;

import org.city.common.api.dto.Response;
import org.city.common.core.controller.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huidihao.handler.api.dto.QuestionDto;
import com.huidihao.handler.core.service.QuestionService;

/**
 * @作者 ChengShi
 * @日期 2022年12月3日
 * @版本 1.0
 * @描述 答题控制
 */
@RestController
@RequestMapping("/data/handler/question")
public class QuestionController extends AbstractController<QuestionService>{
	
	@PostMapping("/getOrderAndPut")
	public Response getOrderAndPut(@Validated QuestionDto questionDto) {
		return OK(s -> s.getOrderAndPut(questionDto));
	}
	
	@PostMapping("/get")
	public Response get(@RequestParam("name") String name, @RequestParam("post") String post) {
		return OK(s -> s.get(name, post));
	}
	
	@PostMapping("/verify")
	public Response verify(@RequestParam("name") String name, @RequestParam("post") String post) {
		return OKV(s -> s.verify(name, post));
	}
}
