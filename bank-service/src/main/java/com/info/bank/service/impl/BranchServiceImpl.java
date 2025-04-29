package com.info.bank.service.impl;

import com.info.bank.entity.Branch;
import com.info.bank.repository.BranchRepository;
import com.info.bank.service.BranchService;
import com.info.dto.constants.Constants;
import com.info.dto.kafka.EmailData;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private static final Logger logger = LoggerFactory.getLogger(BranchServiceImpl.class);

    private final BranchRepository branchRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final KafkaTemplate<String, EmailData> emailDataKafkaTemplate;

    @Override
    @CachePut(value = Constants.CACHE_NAME_BRACNH, key = "#branch.id")
    public Branch save(Branch branch) {
        return branchRepository.save(branch);
    }

    @Override
    @Cacheable(value = Constants.CACHE_NAME_BRACNH, key = "#id")
    public Branch findById(Long id) {
        logger.info("Cache miss for branch ID: {}", id);
        return branchRepository.findById(id).orElse(null);
    }

    @Override
    @Cacheable(value = Constants.CACHE_NAME_BRACNH, key = "'allBranches'")
    public List<Branch> findAll() {
        return branchRepository.findAll();
    }

    @Override
    public List<Integer> findAllBranchRoutingNumbers() {
        return branchRepository.findAll().stream().map(Branch::getRoutingNumber).collect(Collectors.toList());
    }

    @Override
    public List<Branch> findAllByRoutingNumber(List<Integer> routingNumbers) {
        String key = "branch::" + routingNumbers.toString();
        List<Object> cachedList = redisTemplate.opsForList().range(key, 0, -1);

        if (cachedList == null || cachedList.isEmpty()) {
            List<Branch> freshData = findAllByRoutingNumbers(routingNumbers);
            for (Branch brn : freshData) {
                redisTemplate.opsForList().rightPush(key, brn);
            }
            return freshData;
        }

        return cachedList.stream().filter(Branch.class::isInstance).map(Branch.class::cast).collect(Collectors.toList());
    }

    private List<Branch> findAllByRoutingNumbers(List<Integer> routingNumbers) {
        return branchRepository.findAllByRoutingNumber(routingNumbers);
    }


    @Override
    @Cacheable(value = Constants.CACHE_NAME_BRACNH, key = "#routingNumber")
    public Branch findByRoutingNumber(Integer routingNumber) {
        return branchRepository.findByRoutingNumber(routingNumber);
    }

    @Override
    @CacheEvict(value = Constants.CACHE_NAME_BRACNH, key = "#id")
    public void delete(Long id) {
        branchRepository.deleteById(id);
    }
}
