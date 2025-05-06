package com.info.api.service.common;

import com.info.api.entity.MbkBrn;

import java.util.List;

public interface MbkBrnService {
    List<MbkBrn> findAll();
    List<MbkBrn> findAllByMbkbrnKeyBranchRoutingIn(List<String> branchRoutings);
    MbkBrn findByMbkbrnKeyBranchRouting(String branchRouting);

}
