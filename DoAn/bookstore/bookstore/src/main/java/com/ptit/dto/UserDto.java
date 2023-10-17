package com.ptit.dto;

import java.util.HashSet;
import java.util.Set;

import com.ptit.model.Address;
import com.ptit.model.User;

public class UserDto {
	private long userId; 
	private String username; 
	private String phone;
	private String email;

	private String cccd;
	private int age; 
	private String password;
	private boolean gender; 
	Set<Address> setAddress = new HashSet<Address>();
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	public Set<Address> getSetAddress() {
		return setAddress;
	}

	public String getCccd() {
		return cccd;
	}

	public void setCccd(String cccd) {
		this.cccd = cccd;
	}

	public void setSetAddress(Set<Address> setAddress) {
		this.setAddress = setAddress;
	}
	public UserDto(long userId, String username, String phone, String email, String cccd, int age, String password, boolean gender,
			Set<Address> setAddress) {
		super();
		this.userId = userId;
		this.username = username;
		this.phone = phone;
		this.email = email;
		this.cccd = cccd;
		this.age = age;
		this.password = password;
		this.gender = gender;
		this.setAddress = setAddress;
	}
	public UserDto() {
		super();
	} 
	
	
}
