package com.info.userservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.info.userservice.dto.Department;

@FeignClient(name = "DEPARTMENT-SERVICE")
public interface FeignClientDepartmentService {

   @GetMapping("/departments/{id}")
   Department findById(@PathVariable Long id);

}
