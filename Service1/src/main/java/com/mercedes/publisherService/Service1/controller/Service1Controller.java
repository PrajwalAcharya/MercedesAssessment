package com.mercedes.publisherService.Service1.controller;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Queue;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mercedes.publisherService.Service1.model.MessageBean;
import com.mercedes.publisherService.Service1.model.MessageDataBean;
import com.mercedes.publisherService.Service1.protobuf.ProtoMessage.MessageData;
import com.mercedes.publisherService.Service1.service.MessageService;

/**
 * This is the Controller class which is responsible for sending the messages
 * received from the Customer to Service2.
 * 
 * @author PrajwalSAcharya
 *
 */
@RestController
@RequestMapping("/api/connectedCarsPublisher")
public class Service1Controller {

	@Autowired
	private Queue queue;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private MessageService messageService;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * This method is responsible for sending messages through the Active MQ and
	 * saving them to a file.
	 * 
	 * @param messageBean
	 *            Request object containing the message.
	 * @param fileType
	 *            type of File
	 * @return Success response
	 * @throws JsonProcessingException
	 */
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

	/**
	 * This method is responsible for updating existing message in the file.
	 * 
	 * @param id
	 *            id of the object whose details are to be updated.
	 * @param salary
	 *            new value of salary to be updated.
	 * @param fileType
	 *            type of File.
	 * @return Success message
	 * @throws JsonProcessingException
	 */
	@PutMapping(path = "/editMessage/id/{id}/salary/{salary}")
	public ResponseEntity<String> updateMessage(@PathVariable("id") Long id, @PathVariable("salary") double salary,
			@RequestParam String fileType) throws JsonProcessingException {
		MessageBean messageBean = new MessageBean();
		messageBean.setId(id);
		messageBean.setSalary(String.valueOf(salary));
		MessageDataBean messageDataBean = messageService.encryptMessage(messageBean, fileType);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String message = ow.writeValueAsString(messageDataBean);
		jmsTemplate.convertAndSend(queue, message);
		return new ResponseEntity<String>("Successfully updated id " + id + " with salary " + salary,
				HttpStatus.CREATED);
	}

	/**
	 * This method fetches the details of all the existing messages in the file
	 * through Service 2 using Rest Template.
	 * 
	 * @return list of message objects.
	 */
	@GetMapping(path = "/readData", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MessageBean>> readExistingData() {
		ResponseEntity<List<MessageDataBean>> responseEntity = restTemplate.exchange(
				"http://localhost:8085/api/consumer/obtainMessageDetails", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<MessageDataBean>>() {
				});
		List<MessageDataBean> encryptedMessageList = responseEntity.getBody();
		List<MessageBean> messageList = new ArrayList<MessageBean>();
		for (MessageDataBean encryptedBean : encryptedMessageList) {
			MessageBean messageBean = messageService.decryptMessage(encryptedBean);
			messageList.add(messageBean);
		}
		return new ResponseEntity<>(messageList, HttpStatus.OK);
	}

}
