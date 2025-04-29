package com.info.department.service.impl;

import com.info.dto.constants.Constants;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.info.department.model.Department;
import com.info.department.repository.DepartmentRepository;
import com.info.department.service.DepartmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

   private final DepartmentRepository departmentRepository;


   @Override
   @CachePut(value = Constants.CACHE_NAME_DEPARTMENT, key = "#department.id")
   public Department save(Department department) {
      return departmentRepository.save(department);
   }

   @Override
   @Cacheable(value = Constants.CACHE_NAME_DEPARTMENT, key = "#id")
   public Department findById(Long departmentId) {
      return departmentRepository.findById(departmentId).orElse(null);
   }

}
