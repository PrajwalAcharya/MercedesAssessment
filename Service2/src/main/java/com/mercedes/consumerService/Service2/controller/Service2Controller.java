package com.mercedes.consumerService.Service2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercedes.consumerService.Service2.model.MessageDataBean;
import com.mercedes.consumerService.Service2.service.MessageService;

/**
 * This is the controller class of Service 2 which provides message details to
 * Service 1 in encrypted format.
 * 
 * @author PrajwalSAcharya
 *
 */
@RestController
@RequestMapping("/api/consumer")
public class Service2Controller {

	@Autowired
	private MessageService messageService;

	@GetMapping(path = "/obtainMessageDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MessageDataBean>> getMessageDetails() {
		List<MessageDataBean> originalMessageList = messageService.getMessages();
		List<MessageDataBean> encryptedMessageList = new ArrayList<MessageDataBean>();
		for (MessageDataBean message : originalMessageList) {
			MessageDataBean encryptedBean = messageService.encryptMessage(message);
			encryptedMessageList.add(encryptedBean);
		}
		return new ResponseEntity<List<MessageDataBean>>(encryptedMessageList, HttpStatus.OK);
	}

}
