package com.barath.app;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AddressVO {
	
	 private String line1;
	 private String line2;
	 private Integer zipCode;
	 private String state;
	 private String country;
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public Integer getZipCode() {
		return zipCode;
	}
	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public AddressVO(String line1, String line2, Integer zipCode, String state, String country) {
		super();
		this.line1 = line1;
		this.line2 = line2;
		this.zipCode = zipCode;
		this.state = state;
		this.country = country;
	}
	public AddressVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
	 

}
