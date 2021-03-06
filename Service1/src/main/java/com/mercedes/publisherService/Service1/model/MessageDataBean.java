package com.mercedes.publisherService.Service1.model;

public class MessageDataBean {

	private String id;
	private String name;
	private String dob;
	private String salary;
	private String age;
	private String fileType;

	public MessageDataBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageDataBean(String id, String name, String dob, String salary, String age, String fileType) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.salary = salary;
		this.age = age;
		this.fileType = fileType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
