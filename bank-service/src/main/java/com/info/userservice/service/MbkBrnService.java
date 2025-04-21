package com.info.userservice.service;


import com.info.userservice.entity.MbkBrn;
import com.info.userservice.entity.MbkBrnKey;

import java.util.List;

public interface MbkBrnService {
    MbkBrn save(MbkBrn mbkBrn);
    MbkBrn findById(MbkBrnKey mbkBrnKey);
    List<MbkBrn> findAll();
    List<MbkBrn> findAllByMbkbrnKeyBranchRoutingIn(List<String> branchRoutings);
    MbkBrn findByMbkbrnKeyBranchRouting(String branchRouting);

}
