package com.mercedes.publisherService.Service1.configuration;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class contains configuration of JMS where Active MQ is defined.
 * 
 * @author PrajwalSAcharya
 *
 */
@Configuration
public class JmsConfig {

	@Bean
	public Queue queue() {
		return new ActiveMQQueue("test-queue");
	}
}
