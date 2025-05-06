package com.info.api.service.impl;

import com.info.api.entity.MbkBrn;
import com.info.api.repository.MbkBrnRepository;
import com.info.api.service.common.MbkBrnService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.info.dto.constants.Constants.CACHE_NAME_MBKBRN;

@Service
@RequiredArgsConstructor
public class MbkBrnServiceImpl implements MbkBrnService {


    private final MbkBrnRepository mbkBrnRepository;

    @Override
    @Cacheable(value = CACHE_NAME_MBKBRN, key = "'all'")
    public List<MbkBrn> findAll() {
        return mbkBrnRepository.findAll();
    }

    @Override
    @Cacheable(value = CACHE_NAME_MBKBRN, key = "#branchRoutings")
    public List<MbkBrn> findAllByMbkbrnKeyBranchRoutingIn(List<String> branchRoutings) {
        return mbkBrnRepository.findAllByMbkbrnKey_branchRoutingIn(branchRoutings);
    }

    @Override
    @Cacheable(value = CACHE_NAME_MBKBRN, key = "#branchRouting")
    public MbkBrn findByMbkbrnKeyBranchRouting(String branchRouting) {
        return mbkBrnRepository.findAllByMbkbrnKey_branchRouting(branchRouting);
    }


}
