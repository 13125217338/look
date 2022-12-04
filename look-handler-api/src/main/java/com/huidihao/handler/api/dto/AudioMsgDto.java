package com.huidihao.handler.api.dto;

import javax.validation.constraints.Size;

import org.city.common.api.dto.BaseDto;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @作者 ChengShi
 * @日期 2022-11-23 17:56:03
 * @版本 1.0
 * @描述 语音消息参数
 */
@Getter
@Setter
@Accessors(chain = true)
public class AudioMsgDto extends BaseDto {
	private static final long serialVersionUID = 1L;
	/* 主键 */
	private Long id;
	/* 用户名 */
	private String name;
	/* 岗位 */
	private String post;
	/* 消息 */
	@Size(min = 1, max = 100, message = "个人心得必须在1-100字之间！")
	private String msg;
	/* 地点 */
	private String place;
	/* 语音地址 */
	private String audioPath;
	/* 录音名 */
	private String audioName;
	
	/* 音频文件 */
	private MultipartFile file;
}