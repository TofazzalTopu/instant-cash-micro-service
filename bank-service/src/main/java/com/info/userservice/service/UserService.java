package com.info.userservice.service;

import com.info.userservice.dto.UserDTO;
import com.info.userservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

   List<User> findAll();
   User save(User user);
   Optional<User> findById(Long userId);
   UserDTO findUserAndDepartmentById(Long userId);
   UserDTO findUserAndDepartmentByIdWithRestApi(Long userId);
}
