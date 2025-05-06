package com.info.api.repository;

import com.info.api.entity.RemittanceProcessMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RemittanceProcessMasterRepository extends JpaRepository<RemittanceProcessMaster, Long> {

    RemittanceProcessMaster findFirstByProcessDateAndExchangeHouseCodeAndApiDataAndProcessStatusOrderByIdDesc(Date date, String exchangeCode, int apiData, String processStatus);

}
