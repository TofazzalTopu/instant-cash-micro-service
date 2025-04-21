package com.info.userservice.service.impl;

import com.info.userservice.entity.Branch;
import com.info.userservice.repository.BranchRepository;
import com.info.userservice.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;


    @Override
    public Branch save(Branch branch) {
        return branchRepository.save(branch);
    }

    @Override
    public Branch findById(Long id) {
        return branchRepository.findById(id).orElse(null);
    }

    @Override
    public List<Branch> findAll() {
        return branchRepository.findAll();
    }

    @Override
    public List<Integer> findAllBranchRoutingNumbers() {
        return findAll().stream().map(Branch::getRoutingNumber).collect(Collectors.toList());
    }

    @Override
    public List<Branch> findAllByRoutingNumber(List<Integer> routingNumbers) {
        return branchRepository.findAllByRoutingNumber(routingNumbers);
    }

    @Override
    public Branch findByRoutingNumber(Integer routingNumber) {
        return branchRepository.findByRoutingNumber(routingNumber);
    }
}
