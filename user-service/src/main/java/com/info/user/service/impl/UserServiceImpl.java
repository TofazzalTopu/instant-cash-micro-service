package com.info.user.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.info.dto.constants.Constants;
import com.info.dto.department.DepartmentDTO;
import com.info.dto.department.UserDepartmentDTO;
import com.info.dto.user.UserDTO;
import com.info.user.feignClient.DepartmentServiceFeignClient;
import com.info.user.model.User;
import com.info.user.repository.UserRepository;
import com.info.user.service.kafka.KafkaUserService;
import com.info.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final KafkaUserService kafkaUserService;
    private final DepartmentServiceFeignClient departmentServiceFeignClient;

    @Override
    public UserDTO save(UserDTO userDTO) {
        User userEntity = objectMapper.convertValue(userDTO, User.class);
        User user = save(userEntity);
        kafkaUserService.sendEmailNotification(userDTO);
        return objectMapper.convertValue(user, UserDTO.class);
    }

    @CachePut(value = Constants.CACHE_NAME_USER, key = "#user.id")
    private User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Cacheable(value = Constants.CACHE_NAME_USER, key = "#id")
    public UserDTO findById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return objectMapper.convertValue(user, UserDTO.class);
    }

    @Override
    @Cacheable(value = Constants.CACHE_NAME_USER, key = "#department.id")
    public UserDepartmentDTO findUserAndDepartmentById(Long userId) {
        UserDTO userDTO = findById(userId);
        DepartmentDTO department = departmentServiceFeignClient.findById(userDTO.getDepartmentId());
        return new UserDepartmentDTO(userDTO, department);
    }


}
