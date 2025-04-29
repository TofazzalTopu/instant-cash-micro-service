package com.info.auth.service;

import com.info.auth.model.User;
import com.info.dto.user.UserDTO;

import java.util.Optional;

public interface UserService {

	User save(UserDTO user);

	Optional<User> findById(Long userId);

	User findByUsername(String username);
}