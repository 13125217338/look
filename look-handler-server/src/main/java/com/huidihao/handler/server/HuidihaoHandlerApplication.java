package com.huidihao.handler.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @作者 ChengShi
 * @日期 2022-09-26 09:55:23
 * @版本 1.0
 * @描述 处理中心服务入口
 */
@SpringBootApplication(scanBasePackages = {"org.city", "com.huidihao"})
public class HuidihaoHandlerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(HuidihaoHandlerApplication.class, args);
		
		System.out.println("(♥◠‿◠)ﾉﾞ  云MAS接口服务启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
				" .-------.       ____     __        \n" +
				" |  _ _   \\      \\   \\   /  /    \n" +
				" | ( ' )  |       \\  _. /  '       \n" +
				" |(_ o _) /        _( )_ .'         \n" +
				" | (_,_).' __  ___(_ o _)'          \n" +
				" |  |\\ \\  |  ||   |(_,_)'         \n" +
				" |  | \\ `'   /|   `-'  /           \n" +
				" |  |  \\    /  \\      /           \n" +
				" ''-'   `'-'    `-..-'              ");
	
	}
}
