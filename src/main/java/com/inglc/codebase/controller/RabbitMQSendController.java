package com.inglc.codebase.controller;

import static com.inglc.codebase.config.rabbitmq.RabbitMQEnum.INDEX_GOODS_ON_ROUTING_KEY;
import static com.inglc.codebase.config.rabbitmq.RabbitMQEnum.INDEX_GOODS_TOPIC_EXCHANGE;

import com.google.gson.Gson;
import com.inglc.codebase.service.CodeBaseService;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author inglc
 * @date 2020/5/28
 */

@RequestMapping("/rabbitmq")
@RestController
public class RabbitMQSendController {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@GetMapping("/send/msg")
	public ResponseEntity sendMsg(){
		MessageProperties messageProperties = new MessageProperties();
		String uniqueId = UUID.randomUUID().toString();
		messageProperties.setMessageId(uniqueId);
		messageProperties.setTimestamp(new Date());
		messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
		messageProperties.setAppId("SolrIndexer");
		Message message = new Message(new Gson().toJson(Collections.singletonMap("goodsId", String.valueOf(123))).getBytes(), messageProperties);

		messageProperties.setType("1");

		// set correlationData ?
		CorrelationData correlationData = new CorrelationData(uniqueId);
		correlationData.setReturnedMessage(message);

		// 商品上架
		rabbitTemplate.send(INDEX_GOODS_TOPIC_EXCHANGE.getName(), INDEX_GOODS_ON_ROUTING_KEY.getName(), message, correlationData);


		return ResponseEntity.ok("success");
	}

}
