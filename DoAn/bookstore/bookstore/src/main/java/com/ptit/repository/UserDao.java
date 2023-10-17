package com.ptit.repository;

import java.util.List;

import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ptit.model.Book;
import com.ptit.model.User;

@Transactional
public interface UserDao extends JpaRepository<User, Long>{
	@Query("SELECT COUNT(*) FROM User")
    public Long countUsers();
	
	public List<User> findByUsernameOrPhoneAllIgnoreCase(String username, String phone); 
	public List<User> findByUsernameAllIgnoreCase(String username); 
	public List<User> findByPhone(String phone);
	//public List<User> findByCCCD(String cccd);
	public User findByUsername(String username);
	public List<User> findByEmail(String email);
	public Page<User> findByUsernameContainsAllIgnoreCase(String username,Pageable pageable); 
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE bookstore.user\r\n"
			+ "SET age= :age, email= :email, gender = :gender, phone= :phone\r\n"
			+ "WHERE username = :username ", nativeQuery = true)
	public void updateUser(@Param("phone") String phone, @Param("username") String username,@Param("age") int i,@Param("gender") boolean gender,@Param("email") String email);
	
	@Query(value = "select *\r\n"
			+ "from bookstore.user u \r\n"
			+ "where u.user_id not in (select  ur.user_id\r\n"
			+ "						from bookstore.user_role ur\r\n"
			+ "						where ur.id_role = 1)", nativeQuery = true)
	public Page<User> findUserByRoleUser(Pageable pageable);
	
	@Query(value = "select *\r\n"
			+ "from bookstore.user u \r\n"
			+ "where u.user_id in (select  ur.user_id\r\n"
			+ "						from bookstore.user_role ur\r\n"
			+ "						where ur.id_role = 1)", nativeQuery = true)
	public Page<User> findUserByRoleAdmin(Pageable pageable);
	
	@Query(value = "select *\r\n"
			+ "from bookstore.user u \r\n"
			+ "where u.user_id in (select  ur.user_id\r\n"
			+ "						from bookstore.user_role ur\r\n"
			+ "						where ur.id_role = 1)\r\n"
			+ "and u.email like %:gmail%", nativeQuery = true)
	public Page<User> findUserByRoleAdminAndGmail(String gmail,Pageable pageable);
	
	@Query(value = "select *\r\n"
			+ "from bookstore.user u \r\n"
			+ "where u.user_id not in (select  ur.user_id\r\n"
			+ "						from bookstore.user_role ur\r\n"
			+ "						where ur.id_role = 1)\r\n"
			+ "and u.email like %:gmail%", nativeQuery = true)
	public Page<User> findUserByRoleUserAndGmail(String gmail,Pageable pageable);
	
	
	@Query(value = "select u.* from user u where u.email = :email and u.username NOT LIKE :username ", nativeQuery = true)
	public List<User> verifyDuplicateEmail(@Param("email") String email, @Param("username") String username); 
	
	@Query(value = "select u.* from user u where u.phone = :phone and u.username NOT LIKE :username ", nativeQuery = true)
	public List<User> verifyDuplicatePhone(@Param("phone") String phone, @Param("username") String username);

	@Query(value="select u.* from user u where u.cccd =:cccd", nativeQuery = true)
	public List<User> findByCCCD(@Param("cccd") String cccd);
}
