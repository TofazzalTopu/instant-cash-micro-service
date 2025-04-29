package com.info.department.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.info.department.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
