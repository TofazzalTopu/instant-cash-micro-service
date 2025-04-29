package com.info.auth.service.impl;

import com.info.auth.model.User;
import com.info.auth.repository.UserRepository;
import com.info.auth.service.UserService;
import com.info.dto.user.UserDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.info.dto.constants.Constants.CACHE_NAME_AUTH;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder bcryptEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder bcryptEncoder) {
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    @CachePut(value = CACHE_NAME_AUTH, key = "#user.id")
    public User save(UserDTO user) {
        User newUser = new User();
        newUser.setUsername(user.getUserName());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    @Cacheable(value = CACHE_NAME_AUTH, key = "#id")
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    @Cacheable(value = CACHE_NAME_AUTH, key = "#username")
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}
