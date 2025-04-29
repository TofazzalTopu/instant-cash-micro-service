package com.info.bank.feignClient;

import com.info.dto.department.DepartmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DEPARTMENT-SERVICE")
public interface FeignClientDepartmentService {

   @GetMapping("/departments/{id}")
   DepartmentDTO findById(@PathVariable Long id);

}
