package com.huidihao.handler.api.dto;

import lombok.Data;

/**
 * @作者 ChengShi
 * @日期 2022年12月11日
 * @版本 1.0
 * @描述 微信配置参数
 */
@Data
public class WxConfigDto {
	/* 唯一开发Id */
	private String appId = "wx46a4726ec8c83458";
	/* 时间戳 */
	private String timestamp;
	/* 签名随机串 */
	private String nonceStr;
	/* 签名 */
	private String signature;
}
