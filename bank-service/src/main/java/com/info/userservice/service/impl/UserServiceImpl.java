package com.info.userservice.service.impl;

import com.info.userservice.dto.Department;
import com.info.userservice.dto.UserDTO;
import com.info.userservice.feignClient.FeignClientDepartmentService;
import com.info.userservice.entity.User;
import com.info.userservice.repository.UserRepository;
import com.info.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final FeignClientDepartmentService feignClientDepartmentService;

    @Override
    public List<User> findAll() {
        return userRepository.findAll(Sort.sort(User.class).by(User::getId).ascending());
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long userId) {
        log.info("Finding user by id: {}", userId);
        return userRepository.findById(userId);
    }

    @Override
    public UserDTO findUserAndDepartmentById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Department department = feignClientDepartmentService.findById(user.get().getDepartmentId());
            return new UserDTO(user.get(), department);
        }
        return null;
    }

    @Override
    public UserDTO findUserAndDepartmentByIdWithRestApi(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Department department = restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/" + user.get().getDepartmentId(), Department.class);
            return new UserDTO(user.get(), department);
        }
        return null;
    }

}
