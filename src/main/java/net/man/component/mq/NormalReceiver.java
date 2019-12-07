package net.man.component.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RabbitListener(queues = "mall.normal.queue")
public class NormalReceiver {

	@RabbitHandler
	public void handler(String message) {
		log.info("Normal Handler received normal message: " + message);
	}
}
