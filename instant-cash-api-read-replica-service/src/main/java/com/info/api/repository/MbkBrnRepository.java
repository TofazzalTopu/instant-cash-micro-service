package com.info.api.repository;

import com.info.api.entity.MbkBrn;
import com.info.api.entity.MbkBrnKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MbkBrnRepository extends JpaRepository<MbkBrn, MbkBrnKey> {

    List<MbkBrn> findAllByMbkbrnKey_branchRoutingIn(List<String> branchRoutings);
    MbkBrn findAllByMbkbrnKey_branchRouting(String branchRouting);
}
