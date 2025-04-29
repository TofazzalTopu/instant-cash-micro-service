package com.info.api.processor;

import com.info.api.entity.RemittanceData;
import com.info.api.dto.ic.ICExchangePropertyDTO;
import com.info.api.service.ic.*;
import com.info.api.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.info.api.util.PasswordUtil.generateBase64Hash;


@Component
@RequiredArgsConstructor
public class InstantCashAPIProcessor {

    public static final Logger logger = LoggerFactory.getLogger(InstantCashAPIProcessor.class);

    private final ICConfirmPaidStatusService icConfirmPaidStatusService;
    private final ICOutstandingRemittanceService icOutstandingRemittanceService;
    private final ICConfirmTransactionStatusService icConfirmTransactionStatusService;

    @Value("${INSTANT_CASH_API_USER_PASSWORD}")
    String icPassword;


    public void process() {
        logger.info("InstantCashAPIProcessor started..........");
        try {
            ICExchangePropertyDTO icExchangePropertyDTO = ApiUtil.getICExchangeProperties();
            icExchangePropertyDTO.setPassword(icPassword);
            if(ApiUtil.isInValidICExchangeProperties(icExchangePropertyDTO)) return;

            List<RemittanceData> remittanceDataList = icOutstandingRemittanceService.fetchICOutstandingRemittance(icExchangePropertyDTO);
            if (!remittanceDataList.isEmpty()) {
                icConfirmTransactionStatusService.confirmOutstandingTransactionStatus(icExchangePropertyDTO, remittanceDataList);
            }
            icConfirmPaidStatusService.notifyPaidStatus(icExchangePropertyDTO);
            logger.info("InstantCashAPIProcessor ended.");
        } catch (Exception ex) {
            logger.error("InstantCashAPIProcessor error: {} ", ex.getMessage());
        }
    }
}
