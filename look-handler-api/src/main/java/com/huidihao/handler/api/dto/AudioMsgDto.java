package com.huidihao.handler.api.dto;

import javax.validation.constraints.NotBlank;
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
	@NotBlank(message = "用户名不能为空！")
	private String name;
	/* 岗位 */
	@NotBlank(message = "岗位不能为空！")
	private String post;
	/* 消息 */
	@Size(min = 1, max = 100, message = "个人心得必须在1-100字之间！")
	private String msg;
	/* 地点 */
	private String place;
	/* 联系方式 */
	private String contact;
	/* 语音地址 */
	private String audioPath;
	/* 录音名 */
	private String audioName;
	/* 排序 - 越小越低 */
	private Byte sort;
	
	/* 音频文件 */
	private MultipartFile file;
	/* 微信服务器录音 */
	private String audioId;
}
