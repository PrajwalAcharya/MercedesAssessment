package com.mercedes.consumerService.Service2.service;

import com.mercedes.consumerService.Service2.model.MessageBean;
import com.mercedes.consumerService.Service2.model.MessageDataBean;

public interface MessageService {

	public MessageDataBean encryptMessage(MessageBean message);

	public MessageDataBean decryptMessage(MessageDataBean message);

	public void saveMessageToFile(MessageDataBean messageDataBean);

}