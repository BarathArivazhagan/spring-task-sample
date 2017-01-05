package com.barath.app;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CompanyRegVO {
	
	private String companyName;
	private Integer regNumber;
	private String password;
	private String confirmPassword;
	private String type;
	private String email;
	private String dob;
	private boolean enabled;
	private AddressVO address;
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getRegNumber() {
		return regNumber;
	}
	public void setRegNumber(Integer regNumber) {
		this.regNumber = regNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public AddressVO getAddress() {
		return address;
	}
	public void setAddress(AddressVO address) {
		this.address = address;
	}
	public CompanyRegVO(String companyName, Integer regNumber, String password, String confirmPassword, String type,
			String email, String dob, Boolean enabled, AddressVO address) {
		super();
		this.companyName = companyName;
		this.regNumber = regNumber;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.type = type;
		this.email = email;
		this.dob = dob;
		this.enabled = enabled;
		this.address = address;
	}
	public CompanyRegVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
