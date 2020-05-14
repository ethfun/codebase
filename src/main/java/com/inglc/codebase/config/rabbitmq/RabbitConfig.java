package com.inglc.codebase.config.rabbitmq;


import static com.inglc.codebase.config.rabbitmq.RabbitMqEnum.DELTA_INDEX_EXCHANGE;
import static com.inglc.codebase.config.rabbitmq.RabbitMqEnum.GOODS_DELTA_INDEX_ROUTING_KEY;
import static com.inglc.codebase.config.rabbitmq.RabbitMqEnum.USER_DELTA_INDEXER_QUEUE;
import static com.inglc.codebase.config.rabbitmq.RabbitMqEnum.USER_DELTA_INDEX_ROUTING_KEY;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author L.C
 * @date 2020/5/13
 * @Description RabbitMQ配置
 */
@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitConfig extends RabbitProperties {

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setHost(getHost());
		connectionFactory.setUsername(getUsername());
		connectionFactory.setPassword(getPassword());
		connectionFactory.setVirtualHost(getVirtualHost());
//        connectionFactory.setPublisherConfirmType(ConfirmType.CORRELATED);
		return connectionFactory;
	}

	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	public Queue goodsDeltaIndexQueue() {
		Queue goodsDeltaIndexQueue = new Queue(RabbitMqEnum.Goods_DELTA_INDEXER_QUEUE.getName(), true);
		amqpAdmin().declareQueue(goodsDeltaIndexQueue);
		return goodsDeltaIndexQueue;
	}

	@Bean
	public DirectExchange deltaIndexExchange() {
		DirectExchange directExchange = new DirectExchange(DELTA_INDEX_EXCHANGE.getName());
		amqpAdmin().declareExchange(directExchange);
		return directExchange;
	}

	@Bean
	public Binding goodsDeltaIndexExchangeBind() {
		Binding goodsBinding = BindingBuilder.bind(goodsDeltaIndexQueue()).to(deltaIndexExchange())
				.with(GOODS_DELTA_INDEX_ROUTING_KEY.getName());
		amqpAdmin().declareBinding(goodsBinding);
		return goodsBinding;
	}

	@Bean
	public Queue userDeltaIndexQueue() {
		Queue userDeltaIndexQueue = new Queue(USER_DELTA_INDEXER_QUEUE.getName(), true);
		amqpAdmin().declareQueue(userDeltaIndexQueue);
		return userDeltaIndexQueue;
	}

	@Bean
	public Binding userDeltaIndexExchangeBind() {
		Binding userBinding = BindingBuilder.bind(userDeltaIndexQueue()).to(deltaIndexExchange())
				.with(USER_DELTA_INDEX_ROUTING_KEY.getName());
		amqpAdmin().declareBinding(userBinding);
		return userBinding;
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(this.connectionFactory());
		return rabbitTemplate;
	}

}
