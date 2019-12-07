package net.man.controller;

import lombok.extern.slf4j.Slf4j;
import net.man.common.result.CommonResult;
import net.man.service.MqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/mq")
public class MqController {

	@Autowired
	MqService mqService;

	@PostMapping("/send")
	public CommonResult<String> sendToNormalQueue(@RequestParam String message){
		return mqService.sendMessage(message);
	}

}
