package net.man.config;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class MyPrivateKey {
	private String privatekey = "helloworld";

	
}
