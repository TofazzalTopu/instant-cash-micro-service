package com.info.division.service;

import com.info.division.model.Division;

public interface DivisionService {

   Division save(Division division);

   Division findById(Long id);

}
