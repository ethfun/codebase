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
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.ConfirmType;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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

	// 开启@EnableRabbit 用这个
//	@Autowired
//	private ConnectionFactory connectionFactory;


	//region Java config, 注意 amqpAdmin的申明，推荐SpringBoot资源文件配置
	// https://www.cnblogs.com/niugang0920/p/13043708.html
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
			if (ack) {
				log.error("send success data is {}, msg is {}, ack is {}, cause is {}", data.getId(), ack, cause);
			} else {
				log.error("send fail data is {}, ack is {}, cause is {}", data.getId(), ack, cause);
			}
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




	//<editor-fold desc="config delay queue">
	/**
	 * using the queue name as the new routing key.
	 * This will work since any queue is bound to the default exchange with the binding key equal to the queue name.
	 *
	 * The default exchange is a direct exchange with no name (empty string) pre-declared by the broker.
	 * It has one special property that makes it very useful for simple applications:
	 * every queue that is created is automatically bound to it with a routing key which is the same as the queue name.
	 *
	 * @return
	 */
	@Bean
	Queue incomingQueue() {
		return QueueBuilder.durable("queue.test.incoming")
				.withArgument("x-message-ttl", 10000) // Delay until the message is transferred in milliseconds.
//				.withArgument("x-dead-letter-exchange", GOODS_TOPIC_EXCHANGE.getName())
//				.withArgument("x-dead-letter-routing-key", GOODS_ISHIDDEN_ROUTING_KEY.getName())
				.withArgument("x-dead-letter-exchange", "")
				.withArgument("x-dead-letter-routing-key", "queue.index.goods.state.on")
				.build();
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange("exchange.deadLetter");
	}

	@Bean
	Queue deadLetterQueue() {
		return QueueBuilder.durable("queue.test.dead-letter").build();
	}

	@Bean
	Binding binding() {
		return BindingBuilder.bind(incomingQueue()).to(exchange()).with("routing-key.test.dead-letter");
	}
	//</editor-fold>



}
