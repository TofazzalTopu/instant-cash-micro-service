package com.info.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
   private String email;
   private String userName;
   private String password;
   private Long departmentId;
}
