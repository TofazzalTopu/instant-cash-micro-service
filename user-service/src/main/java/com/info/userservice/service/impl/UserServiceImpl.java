package com.info.userservice.service.impl;

import org.springframework.stereotype.Service;

import com.info.userservice.model.User;
import com.info.userservice.repository.UserRepository;
import com.info.userservice.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

   private final UserRepository userRepository;

   @Override
   public User save(User user) {
      return userRepository.save(user);
   }

   @Override
   public User findById(Long userId) {
      return userRepository.findById(userId).orElse(null);
   }

}
