package com.mercedes.consumerService.Service2.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mercedes.consumerService.Service2.EncDec.EncryptDecrypt;
import com.mercedes.consumerService.Service2.model.MessageBean;
import com.mercedes.consumerService.Service2.model.MessageDataBean;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class MessageServiceImpl implements MessageService {

	@Override
	public MessageDataBean encryptMessage(MessageDataBean message) {
		String secret = "ConnectedCars";
		MessageDataBean messageDataBean = new MessageDataBean();
		messageDataBean.setId(EncryptDecrypt.encrypt(String.valueOf(message.getId()), secret));
		messageDataBean.setName(EncryptDecrypt.encrypt(message.getName(), secret));
		messageDataBean.setDob(EncryptDecrypt.encrypt(message.getDob(), secret));
		messageDataBean.setSalary(EncryptDecrypt.encrypt(message.getSalary(), secret));
		messageDataBean.setAge(EncryptDecrypt.encrypt(String.valueOf(message.getAge()), secret));
		messageDataBean.setFileType(EncryptDecrypt.encrypt(message.getFileType(), secret));
		return messageDataBean;
	}

	@Override
	public MessageDataBean decryptMessage(MessageDataBean message) {
		String secret = "ConnectedCars";
		MessageDataBean messageDataBean = new MessageDataBean();
		messageDataBean.setId(EncryptDecrypt.decrypt(String.valueOf(message.getId()), secret));
		messageDataBean.setName(EncryptDecrypt.decrypt(message.getName(), secret));
		messageDataBean.setDob(EncryptDecrypt.decrypt(message.getDob(), secret));
		messageDataBean.setSalary(EncryptDecrypt.decrypt(message.getSalary(), secret));
		messageDataBean.setAge(EncryptDecrypt.decrypt(String.valueOf(message.getAge()), secret));
		messageDataBean.setFileType(EncryptDecrypt.decrypt(message.getFileType(), secret));
		return messageDataBean;
	}

	@Override
	public void saveMessageToFile(MessageDataBean messageDataBean) {
		MessageBean messageBean = new MessageBean();
		messageBean.setId(Long.valueOf(messageDataBean.getId()));
		messageBean.setName(messageDataBean.getName());
		messageBean.setDob(messageDataBean.getDob());
		messageBean.setSalary(messageDataBean.getSalary());
		messageBean.setAge(Integer.valueOf(messageDataBean.getAge()));
		final String CSV_LOCATION = "Mercedes.csv";
		if (messageDataBean.getFileType().equals("CSV")) {
			try {
				FileWriter writer = new FileWriter(CSV_LOCATION);
				ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
				mappingStrategy.setType(MessageBean.class);

				// Arrange column name as provided in below array.
				String[] columns = new String[] { "Id", "Name", "Dob", "Salary", "Age" };
				mappingStrategy.setColumnMapping(columns);
				writer.append("Id, Name, Dob, Salary, Age");
				writer.append("\n");

				// Creating StatefulBeanToCsv object
				StatefulBeanToCsvBuilder<MessageBean> builder = new StatefulBeanToCsvBuilder<MessageBean>(writer);
				StatefulBeanToCsv<MessageBean> beanWriter = builder.withMappingStrategy(mappingStrategy).build();
				try {
					beanWriter.write(messageBean);
				} catch (CsvDataTypeMismatchException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CsvRequiredFieldEmptyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// closing the writer object
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<MessageDataBean> getMessages() {
		final String CSV_LOCATION = "Mercedes.csv";
		List<MessageDataBean> messageList = new ArrayList<MessageDataBean>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(CSV_LOCATION));
			String newline;
			while ((newline = br.readLine()) != null) {
				String[] newLineArray = newline.split(",");
				if (!newLineArray[0].contains("Id")) {
					MessageDataBean messageDataBean = new MessageDataBean();
					messageDataBean.setId(newLineArray[0].substring(1, newLineArray[0].length() - 1));
					messageDataBean.setName(newLineArray[1].substring(1, newLineArray[1].length() - 1));
					messageDataBean.setDob(newLineArray[2].substring(1, newLineArray[2].length() - 1));
					messageDataBean.setSalary(newLineArray[3].substring(1, newLineArray[3].length() - 1));
					messageDataBean.setAge(newLineArray[4].substring(1, newLineArray[4].length() - 1));
					messageDataBean.setFileType("CSV");
					messageList.add(messageDataBean);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messageList;
	}

}
