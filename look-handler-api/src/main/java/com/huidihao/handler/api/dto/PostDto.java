package com.huidihao.handler.api.dto;

import org.city.common.api.dto.BaseDto;

import lombok.Getter;
import lombok.Setter;

/**
 * @作者 ChengShi
 * @日期 2022年12月7日
 * @版本 1.0
 * @描述 岗位实体类
 */
@Setter
@Getter
public class PostDto extends BaseDto {
	private static final long serialVersionUID = 1L;
	/* 主键 */
	private Long id;
	/* 岗位 */
	private String post;
}
