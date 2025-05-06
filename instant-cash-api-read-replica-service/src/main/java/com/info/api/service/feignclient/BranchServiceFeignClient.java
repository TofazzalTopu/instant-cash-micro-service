package com.info.api.service.feignclient;

import com.info.api.dto.RoutingNumberDTO;
import com.info.api.entity.Branch;
import com.info.api.entity.MbkBrn;
import com.info.dto.constants.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "BANK-SERVICE", path = Constants.API_ENDPOINT + Constants.BANK, fallback = BranchServiceFeignClientFallback.class)
public interface BranchServiceFeignClient {

    @PostMapping(Constants.BRANCH + "/by-routing-numbers")
    List<Branch> findAllByRoutingNumber(RoutingNumberDTO routingNumberDTO);

    @PostMapping(Constants.MBK_BRN + "/by-routing-numbers")
    List<MbkBrn> findAllMbkbrnByBranchRouting(RoutingNumberDTO routingNumberDTO);

}
