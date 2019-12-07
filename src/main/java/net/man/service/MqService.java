package net.man.service;

import lombok.extern.slf4j.Slf4j;
import net.man.common.result.CommonResult;
import net.man.component.mq.NormalSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MqService {

	@Autowired
	NormalSender normalSender;

	public CommonResult sendMessage(String message) {
		//todo 执行一系类下单操作
		log.info("process send normal message");
		normalSender.sendMessage(message);
		return CommonResult.success(message, "消息发送成功");
	}
}
