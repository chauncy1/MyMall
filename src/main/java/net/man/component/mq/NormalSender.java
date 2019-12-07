package net.man.component.mq;

import lombok.extern.slf4j.Slf4j;
import net.man.common.enumerate.QueueEnum;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NormalSender {

	@Autowired
	AmqpTemplate amqpTemplate;

	public void sendMessage(String message) {
		amqpTemplate.convertAndSend(QueueEnum.QUEUE_NORMAL.getExchange(), QueueEnum.QUEUE_NORMAL.getRouteKey(), message);
	}
}
