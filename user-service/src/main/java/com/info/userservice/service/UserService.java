package com.info.userservice.service;

import com.info.userservice.model.User;

public interface UserService {

   User save(User user);
   User findById(Long userId);
}
