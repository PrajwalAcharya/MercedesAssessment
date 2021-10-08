package com.mercedes.publisherService.Service1.configuration;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JmsConfig {

	@Bean
	public Queue queue(){
		return new ActiveMQQueue("test-queue");
	}
}
