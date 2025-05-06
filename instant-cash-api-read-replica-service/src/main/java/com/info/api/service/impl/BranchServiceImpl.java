package com.info.api.service.impl;

import com.info.api.entity.Branch;
import com.info.api.repository.BranchRepository;
import com.info.api.service.common.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.info.dto.constants.Constants.CACHE_NAME_BRACNH;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;


    @Override
    public List<Branch> findAll() {
        return branchRepository.findAll();
    }

    @Override
    @Cacheable(value = CACHE_NAME_BRACNH, key = "#findAllBranchRoutingNumbers)")
    public List<Integer> findAllBranchRoutingNumbers() {
        return findAll().stream().map(Branch::getRoutingNumber).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = CACHE_NAME_BRACNH, key = "#routingNumbers)")
    public List<Branch> findAllByRoutingNumber(List<Integer> routingNumbers) {
        return branchRepository.findAllByRoutingNumber(routingNumbers);
    }

    @Override
    @Cacheable(value = CACHE_NAME_BRACNH, key = "#routingNumber)")
    public Branch findByRoutingNumber(Integer routingNumber) {
        return branchRepository.findByRoutingNumber(routingNumber);
    }
}
