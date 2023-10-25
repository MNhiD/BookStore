package com.ptit.serviceImp;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ptit.dto.UserDto;
import com.ptit.exception.ResourceNotFoundException;
import com.ptit.model.Order;
import com.ptit.model.Role;
import com.ptit.model.User;
import com.ptit.model.UserRole;
import com.ptit.repository.OrderDao;
import com.ptit.repository.RoleDao;
import com.ptit.repository.UserDao;
import com.ptit.repository.UserRoleDao;
import com.ptit.service.UserService;

@Service
public class UserServiceImp implements UserService{

	@Autowired
	UserDao userDao;
	
	@Autowired
	RoleDao roleDao; 
	
	@Autowired
	UserRoleDao userRoleDao; 
	
	@Autowired
	OrderDao orderdao;
	

	
	@Override
	public Long countUsers() {
		// TODO Auto-generated method stub
		return userDao.countUsers();
	}
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	@Override
	public boolean validate(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.matches();
	}
	@Override
	public boolean addAccount(String username, String password, String phone,String email) {
		
		
		User user = new User(); 
		
		String passwordEncrypt =BCrypt.hashpw(password, BCrypt.gensalt(12)); 
		user.setUsername(username);
		user.setPassword(passwordEncrypt);
		user.setPhone(phone);
		user.setEmail(email);
		user.setAge(2007);
		
		userDao.save(user); 
		
		User userAfterSave = userDao.findByUsername(username); 
		Role role = roleDao.findByRoleName("ROLE_USER"); 
		UserRole userRole = new UserRole(0, userAfterSave, role);
		userRoleDao.save(userRole); 
		
		return true;
	}

	@Override
	public boolean checkExistAccountInfo(String username, String phone) {
		List<User> list = userDao.findByUsernameOrPhoneAllIgnoreCase(username, phone); 
		if(list.size()>0) return true;
		return false;
	}

	@Override //use for signup
	public boolean checkExistPhoneInfo(String phone) {
		List<User> list = userDao.findByPhone(phone); 
		if(list.size()>0) return true; 
		return false;
	}

	@Override //use for signup
	public boolean checkExistCCCDInfo(String cccd, String username) {
		List<User> list = userDao.findByCCCD(cccd);
		if(list.size()>0) return true;
		return false;
	}


	@Override 
	public boolean checkExistUsernameInfo(String username) {
		List<User> list = userDao.findByUsernameAllIgnoreCase(username); 
		if(list.size()>0) return true; 
		return false;
	}
	
	@Override 
	public boolean checkExistEmailInfo(String email) {
		List<User> list = userDao.findByEmail(email); 
		if(list.size()>0) return true; 
		return false;
	}
	
	@Override
	public User getUserByUsername(String username) {
		return userDao.findByUsername(username); 
	}

	@Override
	public boolean updateUserInfo(String username, String phone, boolean gender, int age) {
		User user = userDao.findByUsername(username);
		//user.setEmail(email);
		//user.setCccd(cccd);
		user.setPhone(phone);
		user.setGender(gender);
		user.setAge(age);
		userDao.save(user); 
		return true;
	}

	@Override
	public boolean checkExistEmailInfo(String email, String username) {
		
		List<User> list = userDao.verifyDuplicateEmail(email, username); 
		
		if(list.size()>0) return true; 
		return false;
	}

	@Override //use for user update info
	public boolean checkExistPhoneInfo(String phone, String username) {
		List<User> list = userDao.verifyDuplicatePhone(phone, username); 
		
		if(list.size()>0) return true; 
		return false;
		
	}

	@Override
	public boolean verifyOldPassword(String oldPassword, String username) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
		User user = userDao.findByUsername(username); 
		
		
		if(passwordEncoder.matches(oldPassword, user.getPassword())) return true; 
		return false;
	}

	@Override
	public boolean updatePassword(String password, String username) {

		String passwordEncrypt = BCrypt.hashpw(password, BCrypt.gensalt(12)); 
		User user = userDao.findByUsername(username); 
		user.setPassword(passwordEncrypt);
		userDao.save(user); 
		return true;
	}
	@Override
	public Page<User> getAllUser(Pageable page){
		return userDao.findAll(page);
	}
	
	@Override
	public List<User> findUser(String key){
		List<User> list=userDao.findByUsernameOrPhoneAllIgnoreCase(key, key);
		return list; 
	}
	
	@Override
	public Page<User> findPage(int pageNo, int pageSize){
		Pageable pageable = PageRequest.of(pageNo -1, pageSize);
		return userDao.findAll(pageable);
	}
	
	@Override
	public Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection){
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) 
				? Sort.by(sortField).ascending() : Sort.by(sortField).descending() ;
		
		Pageable pageable = PageRequest.of(pageNo -1, pageSize,sort);
		return userDao.findAll(pageable);
//		return userDao.findUserByRoleUser(pageable);
	}

	@Override
	public User findById(long id) throws ResourceNotFoundException {
		Optional<User> user = userDao.findById(id);
		
		if(!user.isPresent()) {
			throw new ResourceNotFoundException("User not found by id"); 
		}
		return user.get();
		
	}

	@Override
	public void saveUser(User user) {
		userDao.save(user);
		User userAfterSave = userDao.findByUsername(user.getUsername()); 
		Role role = roleDao.findByRoleName("ROLE_USER"); 
		UserRole userRole = new UserRole(0, userAfterSave, role);
		userRoleDao.save(userRole); 
		
	}

	@Override
	public UserDto convertUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setUserId(user.getUserId());
		userDto.setUsername(user.getUsername());
		userDto.setPhone(user.getPhone());
		userDto.setEmail(user.getEmail());
		userDto.setAge(user.getAge());
		userDto.setPassword(user.getPassword());
		userDto.setGender(user.isGender());
		userDto.setSetAddress(user.getSetAddress());
		return userDto;
	}

	@Override
	public int deleteUser(User user) {
		List<Order> listOrder= orderdao.findByUser(user);
		if(listOrder.size()>0) return 0;
		else {
			List<UserRole> listRole= userRoleDao.findByUser(user); 
			for(UserRole role :listRole ) {
				userRoleDao.delete(role);
			}
			userDao.delete(user);
			return 1;
		}
		
	}

	@Override
	public Page<User> findPaginatedByAdmin(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) 
				? Sort.by(sortField).ascending() : Sort.by(sortField).descending() ;
		
		Pageable pageable = PageRequest.of(pageNo -1, pageSize,sort);
		return userDao.findUserByRoleAdmin(pageable);
	}

	@Override
	public Page<User> findPaginatedByAdminAndGmail(int pageNo, int pageSize, String sortField, String sortDirection,
			String gmail) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) 
				? Sort.by(sortField).ascending() : Sort.by(sortField).descending() ;
		
		Pageable pageable = PageRequest.of(pageNo -1, pageSize,sort);
		return userDao.findUserByRoleAdminAndGmail(gmail, pageable);
	}

	@Override
	public Page<User> findPaginatedByUserAndGmail(int pageNo, int pageSize, String sortField, String sortDirection,
			String gmail) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) 
				? Sort.by(sortField).ascending() : Sort.by(sortField).descending() ;
		
		Pageable pageable = PageRequest.of(pageNo -1, pageSize,sort);
		return userDao.findUserByRoleUserAndGmail(gmail, pageable);
	}

	@Override
	public void updateUser(User user) {
		userDao.updateUser(user.getPhone(), user.getUsername(), user.getAge(), user.isGender(),user.getEmail());
		
	}

	@Override
	public Page<User>  findUserByUsername(int pageNo, int pageSize, String sortField, String sortDirection,String key) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) 
				? Sort.by(sortField).ascending() : Sort.by(sortField).descending() ;
		
		Pageable pageable = PageRequest.of(pageNo -1, pageSize,sort);
		
		return userDao.findByUsernameContainsAllIgnoreCase(key,pageable);
	}

	@Override
	public List<User> findUserByUsername() {
		
		return null;
	}

	

	

}
