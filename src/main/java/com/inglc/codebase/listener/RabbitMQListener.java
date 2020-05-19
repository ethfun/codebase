package com.inglc.codebase.listener;

import com.google.gson.Gson;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author L.C
 * @date 2020/5/7
 */
@Slf4j
@Component
public class RabbitMQListener {



	private static final Gson gson = new Gson();

	/**
	 * 1、Publisher Send to exchange confirmCallback log
	 * 2、Publisher routing to queue fail returnCallback log
	 * 3、Consumer ack
	 * goods delta indexing mq message listener
	 *
	 * queue be declare in RabbitConfig already ,no need to bind again
	 */
//	@RabbitListener(bindings =@QueueBinding(
//			value = @Queue(value = "queue.goods.deltaIndex",durable = "true"),
//			exchange =@Exchange(value = "exchange.index.delta"),
//			key = "goods.index.delta"))
	@RabbitListener(queues = "queue.index.goods.info")
	public void goodsDeltaIndexing(Message message) {
		String goodsIdMapString = new String(message.getBody());
		MessageProperties messageProperties = message.getMessageProperties();
		String messageId = messageProperties.getMessageId();
		log.info("===goodsDeltaIndex consuming start,messageId is {}, message_content{}", messageId,goodsIdMapString);
		try {
			Map<String, String> goodsIdMap = gson.fromJson(goodsIdMapString, Map.class);
			if (org.springframework.util.CollectionUtils.isEmpty(goodsIdMap)) {
				log.warn("goodsDeltaIndex goodsIdMap is null");
				return;
			}
			// @todo business
		} catch (Exception e) {
			// @todo 补偿机制
			log.error("consuming goodsDeltaIndex exception, message_content is {}, exception is:{}", goodsIdMapString, e.getMessage());
			e.printStackTrace();
		}
	}


}
