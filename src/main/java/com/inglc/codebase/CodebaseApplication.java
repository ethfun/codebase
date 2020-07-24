package com.inglc.codebase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients
@ComponentScan(basePackages = "com.inglc.codebase")
@SpringBootApplication
public class CodebaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodebaseApplication.class, args);
	}

}
