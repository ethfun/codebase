package com.inglc.codebase.config.rabbitmq;


import static com.inglc.codebase.config.rabbitmq.RabbitMQEnum.INDEX_GOODS_INFO_ROUTING_KEY;
import static com.inglc.codebase.config.rabbitmq.RabbitMQEnum.INDEX_GOODS_ISHIDDEN_ROUTING_KEY;
import static com.inglc.codebase.config.rabbitmq.RabbitMQEnum.INDEX_GOODS_OFF_ROUTING_KEY;
import static com.inglc.codebase.config.rabbitmq.RabbitMQEnum.INDEX_GOODS_ON_ROUTING_KEY;
import static com.inglc.codebase.config.rabbitmq.RabbitMQEnum.INDEX_GOODS_PRICE_ROUTING_KEY;
import static com.inglc.codebase.config.rabbitmq.RabbitMQEnum.INDEX_GOODS_TOPIC_EXCHANGE;
import static com.inglc.codebase.config.rabbitmq.RabbitMQEnum.INDEX_USER_EXCHANGE;
import static com.inglc.codebase.config.rabbitmq.RabbitMQEnum.INDEX_USER_INFO_QUEUE;
import static com.inglc.codebase.config.rabbitmq.RabbitMQEnum.INDEX_USER_INFO_ROUTING_KEY;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.ConfirmType;
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
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitConfig extends RabbitProperties {

	//region Java config, 注意 amqpAdmin的申明，推荐SpringBoot资源文件配置
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setHost(getHost());
		connectionFactory.setUsername(getUsername());
		connectionFactory.setPassword(getPassword());
		connectionFactory.setVirtualHost(getVirtualHost());
		connectionFactory.setPublisherConfirmType(ConfirmType.CORRELATED);
		connectionFactory.setPublisherReturns(true);
		return connectionFactory;
	}

	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}
	//endregion

	@Bean
	public Queue goodsInfoIndexQueue() {
		return new Queue(RabbitMQEnum.INDEX_GOODS_INFO_QUEUE.getName(), true);
	}

	@Bean
	public Queue oldGoodsDeltaIndexQueue() {
		return new Queue(RabbitMQEnum.OLD_INDEX_GOODS_DELTA_QUEUE.getName(), true);
	}

	@Bean
	public Queue goodsPriceIndexQueue() {
		return new Queue(RabbitMQEnum.INDEX_GOODS_PRICE_QUEUE.getName(), true);
	}

	@Bean
	public Queue goodsOnIndexQueue() {
		return new Queue(RabbitMQEnum.INDEX_GOODS_ON_QUEUE.getName(), true);
	}

	@Bean
	public Queue goodsOffIndexQueue() {
		return new Queue(RabbitMQEnum.INDEX_GOODS_OFF_QUEUE.getName(), true);
	}

	@Bean
	public Queue goodsIsHiddenIndexQueue() {
		return new Queue(RabbitMQEnum.INDEX_GOODS_ISHIDDEN_QUEUE.getName(), true);
	}


	@Bean
	public TopicExchange indexGoodsExchange() {
		return new TopicExchange(INDEX_GOODS_TOPIC_EXCHANGE.getName());
	}

	@Bean
	public Binding goodsInfoIndexExchangeBind() {
		Binding goodsBinding = BindingBuilder.bind(goodsInfoIndexQueue()).to(indexGoodsExchange())
				.with(INDEX_GOODS_INFO_ROUTING_KEY.getName());
		return goodsBinding;
	}

	@Bean
	public Binding goodsPriceIndexExchangeBind() {
		Binding goodsPriceBinding = BindingBuilder.bind(goodsPriceIndexQueue()).to(indexGoodsExchange())
				.with(INDEX_GOODS_PRICE_ROUTING_KEY.getName());
		return goodsPriceBinding;
	}

	@Bean
	public Binding goodsOnIndexExchangeBind() {
		Binding goodsOnBinding = BindingBuilder.bind(goodsOnIndexQueue()).to(indexGoodsExchange())
				.with(INDEX_GOODS_ON_ROUTING_KEY.getName());
		return goodsOnBinding;
	}


	@Bean
	public Binding goodsIsHiddenIndexExchangeBind() {
		Binding goodsOffBinding = BindingBuilder.bind(goodsIsHiddenIndexQueue()).to(indexGoodsExchange())
				.with(INDEX_GOODS_ISHIDDEN_ROUTING_KEY.getName());
		return goodsOffBinding;
	}

	@Bean
	public Binding goodsOffIndexExchangeBind() {
		Binding goodsOffBinding = BindingBuilder.bind(goodsOffIndexQueue()).to(indexGoodsExchange())
				.with(INDEX_GOODS_OFF_ROUTING_KEY.getName());
		return goodsOffBinding;
	}


	@Bean
	public DirectExchange indexUserExchange() {
		DirectExchange directExchange = new DirectExchange(INDEX_USER_EXCHANGE.getName());
		return directExchange;
	}

	@Bean
	public Queue userInfoIndexQueue() {
		Queue userDeltaIndexQueue = new Queue(INDEX_USER_INFO_QUEUE.getName(), true);
		return userDeltaIndexQueue;
	}

	@Bean
	public Binding userDeltaIndexExchangeBind() {
		Binding userBinding = BindingBuilder.bind(userInfoIndexQueue()).to(indexUserExchange())
				.with(INDEX_USER_INFO_ROUTING_KEY.getName());
		return userBinding;
	}


	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(this.connectionFactory());
		rabbitTemplate.setConfirmCallback((data, ack, cause) -> {
			log.info("send success data is {}, ack is {}, cause is {}", data, ack, cause);
		});
		rabbitTemplate.setMandatory(true);
		rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
			log.info(
					"===returnCallBack message body is {}, properties is {}, replyCode is {}, replayText is {}, "
							+ "exchange is {}, routingKey is {}",
					new String(message.getBody()), message.getMessageProperties().toString(), replyCode, replyText,
					exchange, routingKey);
		});
		return rabbitTemplate;
	}

}
