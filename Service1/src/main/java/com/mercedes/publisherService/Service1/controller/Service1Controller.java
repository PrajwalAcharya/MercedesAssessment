package com.mercedes.publisherService.Service1.controller;

import java.util.List;

import javax.jms.Queue;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mercedes.publisherService.Service1.EncDec.EncryptDecrypt;
import com.mercedes.publisherService.Service1.model.MessageBean;
import com.mercedes.publisherService.Service1.model.MessageDataBean;
import com.mercedes.publisherService.Service1.protobuf.ProtoMessage.MessageData;
import com.mercedes.publisherService.Service1.service.MessageService;

@RestController
@RequestMapping("/api/connectedCarsPublisher")
public class Service1Controller {

	@Autowired
	private Queue queue;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private MessageService messageService;

	@PostMapping(path = "/addMessage", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveMessage(@Valid @RequestBody MessageBean messageBean,
			@RequestParam String fileType) throws JsonProcessingException {
		MessageDataBean messageDataBean = messageService.encryptMessage(messageBean, fileType);
		MessageData protoMessage = MessageData.newBuilder().setId(messageDataBean.getId())
				.setName(messageDataBean.getName()).setDob(messageDataBean.getDob())
				.setSalary(messageDataBean.getSalary()).setAge(messageDataBean.getAge())
				.setFileType(messageDataBean.getFileType()).build();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String message = ow.writeValueAsString(messageDataBean);
		jmsTemplate.convertAndSend(queue, message);
		return new ResponseEntity<String>("Successfully saved message to file", HttpStatus.CREATED);
	}

	@PutMapping(path = "/editMessage/id/{id}/salary/{salary}")
	public ResponseEntity<String> updateMessage(@PathVariable("id") Long id, @PathVariable("salary") double salary,
			@RequestParam String fileType) {
		String secret = "ConnectedCars";
		String encryptedId = EncryptDecrypt.encrypt(String.valueOf(id), secret);
		String encryptedSalary = EncryptDecrypt.encrypt(String.valueOf(salary), secret);
		String encryptedFileType = EncryptDecrypt.encrypt(fileType, secret);
		String message = encryptedId + "," + encryptedSalary + "," + encryptedFileType;
		jmsTemplate.convertAndSend(queue, message);
		return new ResponseEntity<String>("Successfully updated id " + id + " with salary " + salary,
				HttpStatus.CREATED);
	}

	@GetMapping(path = "/readData", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MessageBean>> readExistingData() {

	}

}
