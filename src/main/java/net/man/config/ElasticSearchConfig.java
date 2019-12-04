package net.man.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Fixed error: availableProcessors is already set to [4], rejecting [4]
 */
@Configuration
public class ElasticSearchConfig {
	@PostConstruct
	void init() {
		System.setProperty("es.set.netty.runtime.available.processors", "false");
	}
}
