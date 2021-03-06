package com.mercedes.publisherService.Service1.service;

import org.springframework.stereotype.Service;

import com.mercedes.publisherService.Service1.EncDec.EncryptDecrypt;
import com.mercedes.publisherService.Service1.model.MessageBean;
import com.mercedes.publisherService.Service1.model.MessageDataBean;

/**
 * This is the service class of Service1 which contains the various operations
 * like Encryption and Decryption of messsages.
 * 
 * @author PrajwalSAcharya
 *
 */
@Service
public class MessageServiceImpl implements MessageService {

	/**
	 * This method is responsible for encrypting the message.
	 * 
	 * @return encrypted message object.
	 */
	public MessageDataBean encryptMessage(MessageBean message, String fileType) {
		String secret = "ConnectedCars";
		MessageDataBean messageDataBean = new MessageDataBean();
		messageDataBean.setId(EncryptDecrypt.encrypt(String.valueOf(message.getId()), secret));
		messageDataBean.setName(EncryptDecrypt.encrypt(message.getName(), secret));
		messageDataBean.setDob(EncryptDecrypt.encrypt(message.getDob(), secret));
		messageDataBean.setSalary(EncryptDecrypt.encrypt(message.getSalary(), secret));
		messageDataBean.setAge(EncryptDecrypt.encrypt(String.valueOf(message.getAge()), secret));
		messageDataBean.setFileType(EncryptDecrypt.encrypt(fileType, secret));
		return messageDataBean;
	}

	/**
	 * This method is responsible for decrypting the message.
	 * 
	 * @return decrypted message object.
	 */
	public MessageBean decryptMessage(MessageDataBean message) {
		String secret = "ConnectedCars";
		MessageBean messageBean = new MessageBean();
		messageBean.setId(Long.valueOf(EncryptDecrypt.decrypt(message.getId(), secret)));
		messageBean.setName(EncryptDecrypt.decrypt(message.getName(), secret));
		messageBean.setDob(EncryptDecrypt.decrypt(message.getDob(), secret));
		messageBean.setSalary(EncryptDecrypt.decrypt(message.getSalary(), secret));
		messageBean.setAge(Integer.valueOf(EncryptDecrypt.decrypt(message.getAge(), secret)));
		return messageBean;
	}

}
