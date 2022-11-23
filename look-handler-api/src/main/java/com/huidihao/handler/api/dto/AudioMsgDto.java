package com.huidihao.handler.api.dto;

import javax.validation.constraints.NotBlank;

import org.city.common.api.dto.BaseDto;

import lombok.Getter;
import lombok.Setter;

/**
 * @作者 ChengShi
 * @日期 2022-11-23 17:56:03
 * @版本 1.0
 * @描述 语音消息参数
 */
@Getter
@Setter
public class AudioMsgDto extends BaseDto {
	private static final long serialVersionUID = 1L;
	/* 主键 */
	private Long id;
	/* 用户名 */
	@NotBlank(message = "用户名称不能为空！")
	private String userName;
	/* 消息 */
	@NotBlank(message = "消息不能为空！")
	private String msg;
	/* 语音地址 */
	private String audioPath;
	/* 录音名 */
	private String audioName;
}
