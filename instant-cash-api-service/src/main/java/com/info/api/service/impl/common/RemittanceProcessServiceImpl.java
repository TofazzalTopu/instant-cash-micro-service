package com.info.api.service.impl.common;

import com.info.api.entity.RemittanceData;
import com.info.api.entity.RemittanceProcessMaster;
import com.info.api.service.common.ApiTraceService;
import com.info.api.service.common.RemittanceDataService;
import com.info.api.service.common.RemittanceProcessMasterService;
import com.info.api.service.common.RemittanceProcessService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RemittanceProcessServiceImpl implements RemittanceProcessService {
    private static final Logger logger = LoggerFactory.getLogger(RemittanceProcessServiceImpl.class);

    private final ApiTraceService apiTraceService;
    private final RemittanceDataService remittanceDataService;
    private final RemittanceProcessMasterService remittanceProcessMasterService;

    @Override
    public List<RemittanceData> saveAllRemittanceData(List<RemittanceData> remittanceDataList) {
        return remittanceDataService.saveAll(remittanceDataList);
    }

    @Override
    @Transactional
    public List<RemittanceData> processAndSaveRemittanceData(List<RemittanceData> remittanceDataList, String exchangeCode, String exchangeName) {
        if (!remittanceDataList.isEmpty()) {
            RemittanceProcessMaster master = remittanceProcessMasterService.findFirstByProcessDateAndExchangeHouseCodeAndApiDataAndProcessStatus(remittanceDataList.get(0).getProcessDate(), exchangeCode, 1, "OPEN");
            
            long eftCount = remittanceDataList.stream().filter(d -> d.getRemittanceMessageType().equals("EFT")).count();
            long beftnCount = remittanceDataList.stream().filter(d -> d.getRemittanceMessageType().equals("BEFTN")).count();
            long mobileCount = remittanceDataList.stream().filter(d -> d.getRemittanceMessageType().equals("MOBILE")).count();

            if (master == null) {
                master = new RemittanceProcessMaster();
                master.setCommon(true);
                master.setFileName(exchangeName);
                master.setApiData(1);
                master.setManualOpen(0);
                master.setProcessByUser("CBSinfo");
                master.setExchangeHouseCode(exchangeCode);
                master.setProcessDate(remittanceDataList.get(0).getProcessDate());
                master.setProcessStatus("OPEN");
                master.setTotalBeftn(beftnCount);
                master.setTotalEft(eftCount);
                master.setTotalMobile(mobileCount);
                master.setTotalSpot(0);
                master.setTotalWeb(0);
            } else {
                master.setTotalBeftn(master.getTotalBeftn() + beftnCount);
                master.setTotalEft(master.getTotalEft() + eftCount);
                master.setTotalMobile(master.getTotalMobile() + mobileCount);
            }
            master = remittanceProcessMasterService.save(master);

            for (RemittanceData data : remittanceDataList) {
                data.setRemittanceProcessMaster(master);
            }
            remittanceDataList = remittanceDataService.saveAll(remittanceDataList);
        }
        return remittanceDataList;
    }

    @Override
    public RemittanceData saveWebOrSpotData(RemittanceData data, String exchangeCode, String exchangeName) {
        try {
            RemittanceProcessMaster master = null;
            RemittanceProcessMaster masterRecord = remittanceProcessMasterService.findFirstByProcessDateAndExchangeHouseCodeAndApiDataAndProcessStatus(data.getProcessDate(), exchangeCode, 1, "OPEN");
            if (masterRecord != null) {
                master = masterRecord;
            }

            if (master == null) {
                master = new RemittanceProcessMaster();
                master.setCommon(true);
                master.setExchangeHouseCode(exchangeCode);
                master.setFileName(exchangeName);
                master.setApiData(1);
                master.setManualOpen(0);
                master.setProcessByUser("CBSinfo");
                master.setProcessDate(data.getProcessDate());
                master.setProcessStatus("OPEN");
                master.setTotalBeftn(0);
                master.setTotalEft(0);
                master.setTotalMobile(0);
                master.setTotalSpot(0);
                master.setTotalWeb(1);
            } else {
                master.setTotalWeb(master.getTotalWeb() + 1);
            }
            master = remittanceProcessMasterService.save(master);
            data.setRemittanceProcessMaster(master);
            remittanceDataService.save(data);
            apiTraceService.updateSyncFlag(exchangeCode, data.getSecurityCode(), data.getProcessDate());
        } catch (Exception e) {
            logger.info("Error in saveWebOrSpotData. Error = {}", e.getMessage());
        }

        return data;
    }
}
