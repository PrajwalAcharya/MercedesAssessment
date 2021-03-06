package com.mercedes.consumerService.Service2.service;

import java.util.List;

import com.mercedes.consumerService.Service2.model.MessageDataBean;

public interface MessageService {

	public MessageDataBean encryptMessage(MessageDataBean message);

	public MessageDataBean decryptMessage(MessageDataBean message);

	public void saveMessageToFile(MessageDataBean messageDataBean);

	public List<MessageDataBean> getMessages();

}
