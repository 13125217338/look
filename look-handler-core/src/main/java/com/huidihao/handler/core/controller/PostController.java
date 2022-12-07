package com.huidihao.handler.core.controller;

import org.city.common.api.dto.Response;
import org.city.common.core.controller.AbstractController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huidihao.handler.core.service.PostService;

/**
 * @作者 ChengShi
 * @日期 2022年12月7日
 * @版本 1.0
 * @描述 岗位控制
 */
@RestController
@RequestMapping("/data/handler/post")
public class PostController extends AbstractController<PostService>{

	@PostMapping("/getPosts")
	public Response getPosts() {
		return OK(s -> s.getPosts());
	}
}
