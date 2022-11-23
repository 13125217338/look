package com.huidihao.handler.core.entity;

import org.city.common.api.annotation.sql.Table;
import org.city.common.core.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * @作者 ChengShi
 * @日期 2022-11-23 18:08:03
 * @版本 1.0
 * @描述 语音消息实体
 */
@Getter
@Setter
@Table(name = "audio_msg")
public class AudioMsgEntity extends BaseEntity {
	/* 主键 */
	private Long id;
	/* 用户名 */
	private String userName;
	/* 消息 */
	private String msg;
	/* 语音地址 */
	private String audioPath;
	/* 录音名 */
	private String audioName;
}
