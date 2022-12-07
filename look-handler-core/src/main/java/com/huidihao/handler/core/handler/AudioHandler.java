package com.huidihao.handler.core.handler;

import java.io.File;

import org.springframework.stereotype.Component;

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
@Component
public class AudioHandler {
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
}
