package com.huidihao.handler.core.entity;

import org.city.common.api.annotation.sql.Table;
import org.city.common.core.entity.BaseEntity;

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
@Table(name = "question")
public class QuestionEntity extends BaseEntity{
	/* 答题人员 */
	private String name;
	/* 岗位 */
	private String post;
	/* 分数 */
	private Byte score;
	/* 地点 */
	private String place;
	/* 联系方式 */
	private String contact;
}
