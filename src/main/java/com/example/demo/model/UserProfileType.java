package com.example.demo.model;

import java.io.Serializable;

public enum UserProfileType implements Serializable{
	USER("ROLE_USER"),
	MANAGER("ROLE_MANAGER"),
	ADMIN("ROLE_ADMIN");
	
	String userProfileType;
	
	private UserProfileType(String userProfileType){
		this.userProfileType = userProfileType;
	}
	
	public String getUserProfileType(){
		return userProfileType;
	}
	
}
