package com.huidihao.handler.core.entity;

import org.city.common.api.annotation.sql.Table;
import org.city.common.core.entity.BaseEntity;

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
@Table(name = "post")
public class PostEntity extends BaseEntity {
	/* 主键 */
	private Long id;
	/* 岗位 */
	private String post;
	/* 地点 */
	private String place;
}
