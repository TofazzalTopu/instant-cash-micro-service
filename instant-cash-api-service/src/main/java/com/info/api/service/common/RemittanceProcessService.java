package com.info.api.service.common;


import com.info.api.entity.RemittanceData;

import java.util.List;

public interface RemittanceProcessService {

    List<RemittanceData> saveAllRemittanceData(List<RemittanceData> remittanceDataList);

    List<RemittanceData> processAndSaveRemittanceData(List<RemittanceData> remittanceDataList, String exchangeCode, String exchangeName);

    RemittanceData saveWebOrSpotData(RemittanceData data, String exchangeCode, String exchangeName);
}
