package com.info.userservice.service.impl;

import com.info.userservice.entity.MbkBrn;
import com.info.userservice.entity.MbkBrnKey;
import com.info.userservice.repository.MbkBrnRepository;
import com.info.userservice.service.MbkBrnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MbkBrnServiceImpl implements MbkBrnService {


    private final MbkBrnRepository mbkBrnRepository;

    @Override
    public MbkBrn save(MbkBrn mbkBrn) {
        return mbkBrnRepository.save(mbkBrn);
    }

    @Override
    public MbkBrn findById(MbkBrnKey mbkBrnKey) {
        return mbkBrnRepository.findById(mbkBrnKey).orElse(null);
    }

    @Override
    public List<MbkBrn> findAll() {
        return mbkBrnRepository.findAll();
    }

    @Override
    public List<MbkBrn> findAllByMbkbrnKeyBranchRoutingIn(List<String> branchRoutings) {
        return mbkBrnRepository.findAllByMbkbrnKey_branchRoutingIn(branchRoutings);
    }

    @Override
    public MbkBrn findByMbkbrnKeyBranchRouting(String branchRouting) {
        return mbkBrnRepository.findAllByMbkbrnKey_branchRouting(branchRouting);
    }


}
