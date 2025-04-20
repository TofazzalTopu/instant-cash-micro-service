package com.info.division.service.impl;

import org.springframework.stereotype.Service;

import com.info.division.model.Division;
import com.info.division.repository.DivisionRepository;
import com.info.division.service.DivisionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DivisionServiceImpl implements DivisionService {

   private final DivisionRepository divisionRepository;

   @Override
   public Division save(Division division) {
      return divisionRepository.save(division);
   }

   @Override
   public Division findById(Long id) {
      return divisionRepository.findById(id).orElse(null);
   }

}
