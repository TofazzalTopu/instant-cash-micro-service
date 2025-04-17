package com.info.auth.dao;

import com.info.auth.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService extends CrudRepository<User, Integer> {
	
	User findByUsername(String username);
	
}