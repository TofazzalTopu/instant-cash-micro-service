package com.info.dto.department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Department implements Serializable {

   private static final long serialVersionUID = 1744050117179344127L;

   private Long id;

   private String name;
}
