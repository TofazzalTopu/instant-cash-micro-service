package com.info.department.service;

import com.info.department.model.Department;

public interface DepartmentService {

   Department save(Department department);

   Department findById(Long departmentId);

}
