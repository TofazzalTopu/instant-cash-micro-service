package com.info.bank.service;

import com.info.bank.entity.User;
import com.info.dto.department.UserDepartmentDTO;
import com.info.dto.user.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

   List<User> findAll();
   User save(User user);
   Optional<User> findById(Long userId);
   UserDepartmentDTO findUserAndDepartmentById(Long userId);
   UserDepartmentDTO findUserAndDepartmentByIdWithRestApi(Long userId);
}
