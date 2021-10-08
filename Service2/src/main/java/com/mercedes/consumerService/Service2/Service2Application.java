package com.mercedes.consumerService.Service2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mercedes.consumerService.Service2.service.MessageService;
import com.mercedes.consumerService.Service2.service.MessageServiceImpl;

@SpringBootApplication
public class Service2Application {

	public static void main(String[] args) {
		SpringApplication.run(Service2Application.class, args);
	}

	@Bean
	public MessageService messageService() {
		return new MessageServiceImpl();
	}
}
