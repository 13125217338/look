package com.huidihao.handler.core.handler;

import java.io.File;
import java.security.MessageDigest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.huidihao.handler.api.dto.WxConfigDto;

import lombok.extern.slf4j.Slf4j;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

/**
 * @作者 ChengShi
 * @日期 2022年12月7日
 * @版本 1.0
 * @描述 音频处理
 */
@Slf4j
@Component
public class AudioHandler {
	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	/* 令牌地址 */
	private final String TOKEN_URL = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
			"wx46a4726ec8c83458", "a450c74680d5a9d61f65ef8fb8b00fed");
	/* 票据地址 */
	public final static String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
	/* 签名缓存 */
	private final String SING_REDIS_KEY = "Sign:WxAudio";
	/* 票据缓存 */
	private final String TICKET_REDIS_KEY = "Ticket:TkAudio";
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * @描述 音频转Mp3格式
	 * @param in 输入音频
	 * @param out 输出音频
	 * @throws Exception
	 */
	public void conver(File in, File out) throws Exception {
		AudioAttributes attributes = new AudioAttributes();
		attributes.setCodec("libmp3lame"); //mp3
		attributes.setBitRate(128000);
		attributes.setChannels(2);
		attributes.setSamplingRate(44100);
		attributes.setVolume(256);
		EncodingAttributes encodingAttributes = new EncodingAttributes();
		encodingAttributes.setAudioAttributes(attributes);
		new Encoder().encode(new MultimediaObject(in), out, encodingAttributes);
	}
	
	/**
	 * @描述 获取微信配置信息
	 * @param url 当前地址页面
	 * @return 微信配置信息
	 */
	public WxConfigDto getConfig(String url) {
		url = url.split("[#]")[0];
		WxConfigDto configDto = new WxConfigDto();
		configDto.setTimestamp(createTimestamp());
		configDto.setNonceStr(createNonceStr());
		
		String ticket = getJsApiTicket(getToken());
        String signEncodeStr = "jsapi_ticket=" + ticket +
		                 "&noncestr=" + configDto.getNonceStr() +
		                 "&timestamp=" + configDto.getTimestamp() +
		                 "&url=" + url;
        configDto.setSignature(encode(signEncodeStr));
        return configDto;
	}
	
	/**
	 * @描述 获取令牌
	 * @return 令牌
	 */
	public String getToken() {
		String token = (String) redisTemplate.opsForValue().get(SING_REDIS_KEY);
		if (token != null) {return token;}
		
		JSONObject signJson = restTemplate.getForObject(TOKEN_URL, JSONObject.class);
		Integer code = signJson.getInteger("errcode");
		if (code != null && code != 0) {throw new RuntimeException(signJson.getString("errmsg"));}
		/* 缓存签名 */
		redisTemplate.opsForValue().set(SING_REDIS_KEY, token, signJson.getLongValue("expires_in"), TimeUnit.SECONDS);
		return signJson.getString("access_token");
	}
	/* 获取jsApi票据 */
	private String getJsApiTicket(String sign) {
		String ticket = (String) redisTemplate.opsForValue().get(TICKET_REDIS_KEY);
		if (ticket != null) {return ticket;}
		
		log.info("获取到的令牌值》》》 " + sign);
		JSONObject ticketJson = restTemplate.getForObject(String.format(TICKET_URL, sign), JSONObject.class);
		Integer code = ticketJson.getInteger("errcode");
		if (code != null && code != 0) {throw new RuntimeException(ticketJson.getString("errmsg"));}
		/* 缓存签名 */
		redisTemplate.opsForValue().set(SING_REDIS_KEY, sign, ticketJson.getLongValue("expires_in"), TimeUnit.SECONDS);
		return ticketJson.getString("ticket");
	
	}
	
	/* 创建随机串 */
	private static String createNonceStr() {
        return UUID.randomUUID().toString();
    }
	/* 创建时间戳 */
    private static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

	/* 获取格式字符 */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }
	/* SH1编码 */
    private static String encode(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
