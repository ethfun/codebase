package com.inglc.codebase.config.rabbitmq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author L.C
 * @date 2020/5/13
 */
@EnableRabbit
@Configuration
public class RabbitConsumerConfig {

	@Autowired
	private ConnectionFactory connectionFactory;

	/**
	 * @todo
	 *  SimpleRabbitListenerContainerFactory vs DirectRabbitListenerContainerFactory
	 * @return
	 */
	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
		SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory = new
				SimpleRabbitListenerContainerFactory();
		rabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
		rabbitListenerContainerFactory.setPrefetchCount(10);
		rabbitListenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.AUTO);
//        rabbitListenerContainerFactory.setMaxConcurrentConsumers(500);
//        rabbitListenerContainerFactory.setConcurrentConsumers(10);
//        rabbitListenerContainerFactory.setAutoStartup(true);
		rabbitListenerContainerFactory.setConsumerTagStrategy(tagStrategy -> "index.consumer");
		return rabbitListenerContainerFactory;
	}
}
