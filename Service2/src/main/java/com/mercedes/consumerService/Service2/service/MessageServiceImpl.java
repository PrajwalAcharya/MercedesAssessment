package com.mercedes.consumerService.Service2.service;

import java.io.FileWriter;
import java.io.IOException;

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
	public MessageDataBean encryptMessage(MessageBean message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageDataBean decryptMessage(MessageDataBean message) {
		// TODO Auto-generated method stub
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
		messageBean.setSalary(Double.valueOf(messageDataBean.getSalary()));
		messageBean.setAge(Integer.valueOf(messageDataBean.getAge()));
		final String CSV_LOCATION = "Mercedes.csv ";
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

}
