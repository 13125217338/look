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
		Condition cd = new Condition("name", questionDto.getName()).and("post", questionDto.getPost());
		String format = String.format("答题人员[%s]岗位[%s]分数[%d]{?}失败！", questionDto.getName(), questionDto.getPost(), questionDto.getScore());
		/* 已存在更新用户分数 */
		QuestionDto findOne = findOne(cd);
		if (findOne != null) {
			/* 只有答题分数大于原来的分数才更新 */
			if (findOne.getScore() < questionDto.getScore()) {
				QuestionDto dto = new QuestionDto();
				dto.setScore(questionDto.getScore());
				Assert.isTrue(update(cd, dto, false), format.replace("{?}", "更新"));
			}
		} else {
			Assert.isTrue(add(questionDto), format.replace("{?}", "添加"));
		}
		return questionMapper.getOrder(questionDto.getName(), questionDto.getPost());
	}
	
	@Override
	public QuestionDto get(String name, String post) {
		return questionMapper.get(name, post);
	}
	
	@Override
	public void verify(String name, String post) {
		Condition cd = new Condition("name", name).and("post", post);
		Assert.isTrue(count(cd) == 0, String.format("当前岗位[%s]人员[%s]已经答题过了，无法再次答题！", post, name));
	}
	
	@Override
	public List<QuestionDto> list(QuestionDto questionDto) {
		List<QuestionDto> findAll = findAll(new Condition().limit(questionDto).orderBy(new JoinTable(this, null), "score", false));
		for (int i = 0, j = findAll.size(); i < j; i++) {findAll.get(i).setNum(i + 1);}
		return findAll;
	}
}
