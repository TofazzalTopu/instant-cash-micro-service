package com.info.division.service.impl;

import com.info.division.model.Division;
import com.info.division.repository.DivisionRepository;
import com.info.division.service.DivisionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.info.dto.constants.Constants.CACHE_NAME_DIVISION;

@Slf4j
@Service
@RequiredArgsConstructor
public class DivisionServiceImpl implements DivisionService {

    private final DivisionRepository divisionRepository;

    @Override
    @CachePut(value = CACHE_NAME_DIVISION, key = "#division.id")
    public Division save(Division division) {
        return divisionRepository.save(division);
    }

    @Override
    @Cacheable(value = CACHE_NAME_DIVISION, key = "#id")
    public Division findById(Long id) {
        return divisionRepository.findById(id).orElse(null);
    }

}
