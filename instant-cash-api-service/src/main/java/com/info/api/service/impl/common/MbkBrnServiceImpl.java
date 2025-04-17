package com.info.api.service.impl.common;

import com.info.api.entity.MbkBrn;
import com.info.api.repository.MbkBrnRepository;
import com.info.api.service.common.MbkBrnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MbkBrnServiceImpl implements MbkBrnService {


    private final MbkBrnRepository mbkBrnRepository;

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
