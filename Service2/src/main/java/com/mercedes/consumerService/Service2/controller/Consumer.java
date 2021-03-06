package com.mercedes.consumerService.Service2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercedes.consumerService.Service2.model.MessageDataBean;
import com.mercedes.consumerService.Service2.service.MessageService;

/**
 * This is the Consumer class of Service 2 which receives messages from Service
 * 1 via Active MQ and saves or updates messages in CSV file.
 * 
 * @author PrajwalSAcharya
 *
 */
@Component
@EnableJms
public class Consumer {

	@Autowired
	private MessageService messageService;

	@JmsListener(destination = "test-queue")
	public void listener(String message) {
		ObjectMapper mapper = new ObjectMapper();
		MessageDataBean messageData = null;
		try {
			messageData = mapper.readValue(message, MessageDataBean.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MessageDataBean messageDataBean = messageService.decryptMessage(messageData);
		messageService.saveMessageToFile(messageDataBean);
	}

}
