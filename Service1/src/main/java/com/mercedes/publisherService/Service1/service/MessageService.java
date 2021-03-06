package com.mercedes.publisherService.Service1.service;

import com.mercedes.publisherService.Service1.model.MessageBean;
import com.mercedes.publisherService.Service1.model.MessageDataBean;

public interface MessageService {

	public MessageDataBean encryptMessage(MessageBean message, String fileType);

	public MessageBean decryptMessage(MessageDataBean message);

}
