package com.ptit.service;

import com.ptit.exception.ResourceNotFoundException;
import com.ptit.model.Address;
import com.ptit.model.User;

public interface AddressService {
	public Address createAddress(String addressName, String villageId); 
	public void addOrUpdateAddress();
	int addOrUpdateAddress(Address address, User user);
	public boolean deleteAddress(Address address);
	public Address findById(long id) throws ResourceNotFoundException;
}
