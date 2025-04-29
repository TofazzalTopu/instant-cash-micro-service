package com.info.bank.service;


import com.info.bank.entity.Branch;

import java.util.List;

public interface BranchService {

    Branch save(Branch branch);

    Branch findById(Long id);

    List<Branch> findAll();

    List<Integer> findAllBranchRoutingNumbers();

    List<Branch> findAllByRoutingNumber(List<Integer> routingNumbers);

    Branch findByRoutingNumber(Integer routingNumber);

    void delete(Long id);

}
