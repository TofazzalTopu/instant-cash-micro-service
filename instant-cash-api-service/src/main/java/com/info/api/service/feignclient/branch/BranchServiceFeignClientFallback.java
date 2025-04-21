package com.info.api.service.feignclient.branch;

import com.info.api.dto.RoutingNumberDTO;
import com.info.api.entity.Branch;
import com.info.api.entity.MbkBrn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class BranchServiceFeignClientFallback implements BranchServiceFeignClient {
    private static final Logger logger = LoggerFactory.getLogger(BranchServiceFeignClientFallback.class);

    @Override
    public List<Branch> findAllByRoutingNumber(RoutingNumberDTO routingNumberDTO) {
        logger.info("Bank service is taking longer time then expected. Please try again later");
        return Collections.emptyList();
    }

    @Override
    public List<MbkBrn> findAllMbkbrnByBranchRouting(RoutingNumberDTO routingNumberDTO) {
        logger.info("Bank service is taking longer time then expected. Please try again later");
        return Collections.emptyList();
    }


}
