package com.info.userservice.dto;

import com.info.userservice.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

   private User user;
   private Department department;
}
