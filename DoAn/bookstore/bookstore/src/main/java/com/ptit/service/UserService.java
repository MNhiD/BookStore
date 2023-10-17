package com.ptit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.ptit.dto.UserDto;
import com.ptit.exception.ResourceNotFoundException;
import com.ptit.model.Book;
import com.ptit.model.User;

public interface UserService {
	public Long countUsers();
	public boolean addAccount(String username, String password, String phone);
	public boolean checkExistAccountInfo(String username, String phone); 
	
	public boolean checkExistEmailInfo(String email); 
	public boolean checkExistPhoneInfo(String phone);

	//use for signup
	boolean checkExistCCCDInfo(String cccd, String username);

	public boolean checkExistUsernameInfo(String username);
	public User getUserByUsername(String username); 
	public boolean updateUserInfo(String username, String email, String cccd, String phone, boolean gender, int age );
	public boolean checkExistEmailInfo(String email, String username);
	public boolean checkExistPhoneInfo(String phone, String username); 
	public boolean verifyOldPassword(String oldPassword, String username); 
	public boolean updatePassword(String password, String username); 
	
	public Page<User> getAllUser(Pageable page); 
	public List<User> findUser(String key); 
	public Page<User> findPage(int pageNo, int pageSize);
	public Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
	
	public Page<User> findPaginatedByAdmin(int pageNo, int pageSize, String sortField, String sortDirection);
	
	public Page<User> findPaginatedByAdminAndGmail(int pageNo, int pageSize, String sortField, String sortDirection,String gmail);
	public Page<User> findPaginatedByUserAndGmail(int pageNo, int pageSize, String sortField, String sortDirection,String gmail);
	
	public User findById(long id) throws ResourceNotFoundException;
	public void saveUser(User user);
	public UserDto convertUserDto(User user);
	public int deleteUser(User user);
	public void updateUser(User user);
	
	public Page<User> findUserByUsername(int pageNo, int pageSize, String sortField, String sortDirection,String key);
	List<User> findUserByUsername();
}
