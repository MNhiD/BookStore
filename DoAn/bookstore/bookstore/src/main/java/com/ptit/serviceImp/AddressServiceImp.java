package com.ptit.serviceImp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptit.exception.ResourceNotFoundException;
import com.ptit.model.Address;
import com.ptit.model.User;
import com.ptit.model.Village;
import com.ptit.repository.AddressDao;
import com.ptit.repository.VillageDao;
import com.ptit.service.AddressService;
import com.ptit.service.UserService;

@Service
public class AddressServiceImp implements AddressService{
	
	@Autowired
	VillageDao villageDao; 
	
	@Autowired
	AddressDao addressDao; 
	
	@Autowired
	UserService userService;
	@Override
	public Address createAddress(String addressName,String villageId) {
		Address address = new Address(); 
		Village village = villageDao.getById(villageId); 
		
		address.setAddressName(addressName);
		address.setVillage(village);
		return addressDao.save(address); 
		
	}
	@Override
	public int addOrUpdateAddress(Address address, User user) {
		Optional<Address> add = addressDao.findById(address.getAddressId());

		if(!add.isPresent()) {
			addressDao.save(address);
			user.addAddress(address);
			userService.saveUser(user);
			return 1;//thêm
			
		}else {
			address.setSetUsers(add.get().getSetUsers());
			addressDao.save(address);
			return 2;//sửa
		}
		
		
	}
	@Override
	public void addOrUpdateAddress() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean deleteAddress(Address address) {
		addressDao.delete(address);
		
		return true;
	}
	@Override
	public Address findById(long id) throws ResourceNotFoundException {
		Optional<Address> address = addressDao.findById(id);

		if(!address.isPresent()) {
			throw new ResourceNotFoundException("Address not found by id"); 
		}
		return address.get();
	
	}

}

























