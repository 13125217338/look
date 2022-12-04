package com.huidihao.handler.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.city.common.api.dto.BaseDto;

import lombok.Getter;
import lombok.Setter;

/**
 * @作者 ChengShi
 * @日期 2022年12月3日
 * @版本 1.0
 * @描述 答题参数
 */
@Getter
@Setter
public class QuestionDto extends BaseDto{
	private static final long serialVersionUID = 1L;
	/* 答题人员 */
	@NotBlank(message = "答题人员不能为空！")
	private String name;
	/* 分数 */
	@NotNull(message = "未找到答题分数数据！")
	private Byte score;
	/* 地点 */
	@NotBlank(message = "未找到地点信息！")
	private String place;
	
	/* 排名 */
	private Integer num;
}
