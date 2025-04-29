package com.info.user.dto;

import com.info.user.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

   private User user;
   private Department department;
}
