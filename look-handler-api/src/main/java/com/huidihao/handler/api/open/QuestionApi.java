package com.huidihao.handler.api.open;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.huidihao.handler.api.dto.QuestionDto;

/**
 * @作者 ChengShi
 * @日期 2022年12月3日
 * @版本 1.0
 * @描述 答题Api
 */
public interface QuestionApi {
	/**
	 * @描述 添加并获取排名
	 * @param questionDto 答题参数
	 * @return 排名
	 */
	public int getOrderAndPut(@Validated QuestionDto questionDto);
	
	/**
	 * @描述 获取答题参数
	 * @param name 答题人员
	 * @return 答题参数
	 */
	public QuestionDto get(String name);
	
	/**
	 * @描述 获取所有答题参数
	 * @param questionDto 答题参数
	 * @return 所有答题参数
	 */
	public List<QuestionDto> list(QuestionDto questionDto);
}
