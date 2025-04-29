package com.info.dto.department;

import com.info.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDepartmentDTO implements Serializable {

   private static final long serialVersionUID = 1744050117179344127L;

   private UserDTO user;

   private DepartmentDTO department;
}
