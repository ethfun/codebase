package com.inglc.codebase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = "com.inglc.codebase")
@SpringBootApplication(exclude = {RedisAutoConfiguration.class, RabbitAutoConfiguration.class})
public class CodebaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodebaseApplication.class, args);
	}

}
