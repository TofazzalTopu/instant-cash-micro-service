package com.info.api.service.common;


import com.info.api.entity.RemittanceProcessMaster;

import java.util.Date;

public interface RemittanceProcessMasterService {

    RemittanceProcessMaster save(RemittanceProcessMaster master);

    RemittanceProcessMaster findFirstByProcessDateAndExchangeHouseCodeAndApiDataAndProcessStatus(Date date, String exchangeCode, int apiData, String processStatus);

}
