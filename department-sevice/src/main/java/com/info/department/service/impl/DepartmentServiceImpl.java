package com.info.department.service.impl;

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
   public Department save(Department department) {
      return departmentRepository.save(department);
   }

   @Override
   public Department findById(Long departmentId) {
      return departmentRepository.findById(departmentId).orElse(null);
   }

}
