package com.info.user.service;

import com.info.dto.department.UserDepartmentDTO;
import com.info.dto.user.UserDTO;

public interface UserService {

   UserDTO save(UserDTO user);
   UserDTO findById(Long userId);

   UserDepartmentDTO findUserAndDepartmentById(Long userId);
}
