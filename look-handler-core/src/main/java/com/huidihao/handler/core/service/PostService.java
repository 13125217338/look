package com.huidihao.handler.core.service;

import java.util.List;
import java.util.stream.Collectors;

import org.city.common.api.dto.Condition;
import org.city.common.core.service.AbstractService;
import org.springframework.stereotype.Service;

import com.huidihao.handler.api.dto.PostDto;
import com.huidihao.handler.api.open.PostApi;
import com.huidihao.handler.core.entity.PostEntity;

/**
 * @作者 ChengShi
 * @日期 2022年12月7日
 * @版本 1.0
 * @描述 岗位服务
 */
@Service
public class PostService extends AbstractService<PostDto, PostEntity> implements PostApi{

	@Override
	public List<String> getPosts(String place) {
		return findAll(new Condition("place", place)).stream().map(v -> v.getPost()).collect(Collectors.toList());
	}
}
