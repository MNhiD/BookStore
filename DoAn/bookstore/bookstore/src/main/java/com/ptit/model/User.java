package com.ptit.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ptit.dto.UserDto;

import lombok.Data;

@Entity
@Table(name="user")
public class User implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private long userId; 
	
	@NotEmpty
	private String username; 
	
	@Size(min=10,max=10)
	private String phone; 
	
	@Email
	private String email;

	@Size(min=9,max=12)
	@Column(name="CCCD")
	private String cccd;


	@Min(10)
	private int age; 
	
	@Size(max=250)
	private String password; 
	
	
	
	private boolean gender; 
	
	
	//avoid infinitive loop
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "user")
	private Set<Items> setItems = new HashSet<Items>(); 
	
	//@JsonIgnore
	 @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	   @JoinTable(name = "user_address",
	            joinColumns = @JoinColumn(name = "user_id"),  
	            inverseJoinColumns = @JoinColumn(name = "address_id")
	    )
	Set<Address> setAddress = new HashSet<Address>(); 
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "user")
	private Set<Order> setOrders = new HashSet<Order>();

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

	public Set<Items> getSetItems() {
		return setItems;
	}

	public void setSetItems(Set<Items> setItems) {
		this.setItems = setItems;
	}

	public Set<Address> getSetAddress() {
		return setAddress;
	}
	
	public void addAddress(Address address) {
		this.setAddress.add(address);
	}
	
	public void removeAddress(Address address) {
		this.setAddress.remove(address);
	}

	public void setSetAddress(Set<Address> setAddress) {
		this.setAddress = setAddress;
	}

	public Set<Order> getSetOrders() {
		return setOrders;
	}

	public void setSetOrders(Set<Order> setOrders) {
		this.setOrders = setOrders;
	}

	public String getCccd() {
		return cccd;
	}

	public void setCccd(String cccd) {
		this.cccd = cccd;
	}

	public User(long userId, @NotEmpty String username, @Size(min = 10, max = 10) String phone, @Size(min=9,max=12) String cccd, @Email String email,
				@Min(10) int age, @Size(max = 250) String password, boolean gender, Set<Items> setItems,
				Set<Address> setAddress, Set<Order> setOrders) {
		super();
		this.userId = userId;
		this.username = username;
		this.phone = phone;
		this.email = email;
		this.age = age;
		this.password = password;
		this.gender = gender;
		this.setItems = setItems;
		this.setAddress = setAddress;
		this.setOrders = setOrders;
		this.cccd = cccd;
	}

	public User() {
		super();
	} 
	 
	 
	public void convertUser(UserDto userDto) {
		this.setUserId(userDto.getUserId());
		this.setUsername(userDto.getUsername());
		this.setPhone(userDto.getPhone());
		this.setEmail(userDto.getEmail());
		this.setAge(userDto.getAge());
		this.setPassword(userDto.getPassword());
		this.setGender(userDto.isGender());
		this.setSetAddress(userDto.getSetAddress());
		this.setCccd(userDto.getCccd());
	}
	 
	 
}

















