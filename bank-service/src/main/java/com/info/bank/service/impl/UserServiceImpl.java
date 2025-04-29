package com.info.bank.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.info.bank.entity.User;
import com.info.bank.feignClient.FeignClientDepartmentService;
import com.info.bank.repository.UserRepository;
import com.info.bank.service.UserService;
import com.info.dto.constants.Constants;
import com.info.dto.department.DepartmentDTO;
import com.info.dto.department.UserDepartmentDTO;
import com.info.dto.user.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final FeignClientDepartmentService feignClientDepartmentService;

    @Override
    @Cacheable(value = Constants.CACHE_NAME_USER, key = "'all'")
    public List<User> findAll() {
        return userRepository.findAll(Sort.sort(User.class).by(User::getId).ascending());
    }

    @Override
    @CachePut(value = Constants.CACHE_NAME_USER, key = "#user.id")
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Cacheable(value = Constants.CACHE_NAME_USER, key = "#id")
    public Optional<User> findById(Long userId) {
        log.info("Finding user by id: {}", userId);
        return userRepository.findById(userId);
    }

    @Override
    public UserDepartmentDTO findUserAndDepartmentById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            UserDTO userDTO = objectMapper.convertValue(user.get(), UserDTO.class);
            DepartmentDTO department = feignClientDepartmentService.findById(user.get().getDepartmentId());
            return new UserDepartmentDTO(userDTO, department);
        }
        return null;
    }

    @Override
    public UserDepartmentDTO findUserAndDepartmentByIdWithRestApi(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            DepartmentDTO department = restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/" + user.get().getDepartmentId(), DepartmentDTO.class);
            UserDTO userDTO = objectMapper.convertValue(user.get(), UserDTO.class);
            return new UserDepartmentDTO(userDTO, department);
        }
        return null;
    }

}
