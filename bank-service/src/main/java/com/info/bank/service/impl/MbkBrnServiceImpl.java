package com.info.bank.service.impl;

import com.info.bank.entity.MbkBrn;
import com.info.bank.entity.MbkBrnKey;
import com.info.bank.repository.MbkBrnRepository;
import com.info.bank.service.MbkBrnService;
import com.info.dto.constants.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MbkBrnServiceImpl implements MbkBrnService {

    private final MbkBrnRepository mbkBrnRepository;
    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    @CachePut(value = Constants.CACHE_NAME_MBKBRN, key = "#mbkBrn.id")
    public MbkBrn save(MbkBrn mbkBrn) {
        return mbkBrnRepository.save(mbkBrn);
    }

    @Override
    @Cacheable(value = Constants.CACHE_NAME_MBKBRN, key = "#id")
    public MbkBrn findById(MbkBrnKey mbkBrnKey) {
        return mbkBrnRepository.findById(mbkBrnKey).orElse(null);
    }

    @Override
    @Cacheable(value = Constants.CACHE_NAME_MBKBRN, key = "'allMbkBrnes'")
    public List<MbkBrn> findAll() {
        return mbkBrnRepository.findAll();
    }

    @Override
//    @Cacheable(value = Constants.CACHE_NAME_MBKBRN, key = "#branchRoutings")
    public List<MbkBrn> findAllByMbkbrnKeyBranchRoutingIn(List<String> branchRoutings) {
        return findAllByMbkbrnKeyBranchRoutingIns(branchRoutings);
    }

    public List<MbkBrn> findAllByMbkbrnKeyBranchRoutingIns(List<String> branchRoutings) {
        String key = "mbkBrn::" + String.join(",", branchRoutings);
        List<Object> cachedList = redisTemplate.opsForList().range(key, 0, -1);

        if (cachedList == null || cachedList.isEmpty()) {

            List<MbkBrn> freshData = findAllByRoutingNumbers(branchRoutings);
            for (MbkBrn brn : freshData) {
                redisTemplate.opsForList().rightPush(key, brn);
            }
            return freshData;
        }

        return cachedList.stream()
                .filter(MbkBrn.class::isInstance)
                .map(MbkBrn.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = Constants.CACHE_NAME_MBKBRN, key = "#routingNumbers")
    public MbkBrn findByMbkbrnKeyBranchRouting(String branchRouting) {
        return mbkBrnRepository.findAllByMbkbrnKey_branchRouting(branchRouting);
    }

    private List<MbkBrn> findAllByRoutingNumbers(List<String> branchRoutings) {
        return mbkBrnRepository.findAllByMbkbrnKey_branchRoutingIn(branchRoutings);
    }


}
