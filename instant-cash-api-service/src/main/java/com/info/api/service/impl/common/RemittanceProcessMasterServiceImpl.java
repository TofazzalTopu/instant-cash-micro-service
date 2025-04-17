package com.info.api.service.impl.common;

import com.info.api.entity.RemittanceProcessMaster;
import com.info.api.repository.RemittanceProcessMasterRepository;
import com.info.api.service.common.RemittanceProcessMasterService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RemittanceProcessMasterServiceImpl implements RemittanceProcessMasterService {

    private static final Logger logger = LoggerFactory.getLogger(RemittanceProcessMasterServiceImpl.class);

    private final RemittanceProcessMasterRepository remittanceProcessMasterRepository;

    @Override
    public RemittanceProcessMaster save(RemittanceProcessMaster master) {
        return remittanceProcessMasterRepository.save(master);
    }

    @Override
    public RemittanceProcessMaster findFirstByProcessDateAndExchangeHouseCodeAndApiDataAndProcessStatus(Date date, String exchangeCode, int apiData, String processStatus) {
        return remittanceProcessMasterRepository.findFirstByProcessDateAndExchangeHouseCodeAndApiDataAndProcessStatusOrderByIdDesc(date, exchangeCode, apiData, processStatus);
    }


}
