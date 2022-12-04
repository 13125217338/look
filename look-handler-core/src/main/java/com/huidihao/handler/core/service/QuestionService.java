package com.huidihao.handler.core.service;

import java.util.List;

import org.city.common.api.dto.Condition;
import org.city.common.api.dto.Condition.JoinTable;
import org.city.common.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.huidihao.handler.api.dto.QuestionDto;
import com.huidihao.handler.api.open.QuestionApi;
import com.huidihao.handler.core.entity.QuestionEntity;
import com.huidihao.handler.core.mapper.QuestionMapper;

/**
 * @作者 ChengShi
 * @日期 2022年12月3日
 * @版本 1.0
 * @描述 答题服务
 */
@Service
public class QuestionService extends AbstractService<QuestionDto, QuestionEntity> implements QuestionApi {
	@Autowired
	private QuestionMapper questionMapper;
	
	@Override
	@Transactional
	public int getOrderAndPut(QuestionDto questionDto) {
		Assert.isTrue(add(questionDto), String.format("答题人员[%s]分数[%d]添加失败！", questionDto.getName(), questionDto.getScore()));
		return questionMapper.getOrder(questionDto.getName());
	}
	
	@Override
	public QuestionDto get(String name) {
		return questionMapper.get(name);
	}
	
	@Override
	public List<QuestionDto> list(QuestionDto questionDto) {
		List<QuestionDto> findAll = findAll(new Condition().limit(questionDto).orderBy(new JoinTable(this, null), "score", false));
		for (int i = 0, j = findAll.size(); i < j; i++) {findAll.get(i).setNum(i + 1);}
		return findAll;
	}
}
