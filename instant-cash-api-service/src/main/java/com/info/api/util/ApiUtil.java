package com.info.api.util;

import com.info.dto.constants.Constants;
import com.info.api.dto.ic.ICExchangePropertyDTO;
import com.info.api.dto.ria.RiaExchangePropertyDTO;
import com.info.api.entity.ExchangeHouseProperty;
import com.info.api.service.impl.common.LoadExchangeHouseProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.info.dto.constants.RemittanceDataStatus.*;


public class ApiUtil {

    private ApiUtil() {
    }

    public static final Logger logger = LoggerFactory.getLogger(ApiUtil.class);

    public static final String IC_OUTSTANDING_API = "IC_OUTSTANDING_API";
    public static final String IC_CONFIRM_API = "IC_CONFIRM_API";
    public static final String IC_STATUS_API = "IC_STATUS_API";
    public static final String IC_UNLOCK_API = "IC_UNLOCK_API";
    public static final String IC_RECEIVE_PAYMENT_API = "IC_RECEIVE_PAYMENT_API";
    public static final String IC_SUB_KEY_PRIMARY = "IC_OCP_APIM_SUB_KEY_PRIMARY";
    public static final String IC_AGENT_ID = "IC_AGENT_ID";
    public static final String IC_PAYMENT_API_AGENT_ID = "IC_PAYMENT_API_AGENT_ID";
    public static final String IC_TRANSACTION_REPORT_API = "IC_TRANSACTION_REPORT_API";
    public static final String IC_USER_ID = "IC_USER_ID";

    public static final String RIA_API_VERSION = "RIA_API_VERSION";
    public static final String RIA_AGENT_ID = "RIA_AGENT_ID";
    public static final String RIA_DOWNLOADABLE_API = "RIA_DOWNLOADABLE_API";
    public static final String RIA_SEARCH_API = "RIA_SEARCH_API";
    public static final String RIA_PAYMENT_API = "RIA_PAYMENT_API";
    public static final String RIA_OCP_APIM_SUB_KEY = "RIA_OCP_APIM_SUB_KEY";
    public static final String RIA_CASH_PICKUP_CANCEL_API = "RIA_CASH_PICKUP_CANCEL_API";
    public static final String RIA_NOTIFY_REM_STATUS_API = "RIA_NOTIFY_REM_STATUS_API";
    public static final String RIA_CANCEL_REQ_API = "RIA_CANCEL_REQ_API";


    public static HttpHeaders createICHeader(Long correlationId, String apiFinancialId, String apiKey, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-fapi-financial-id", apiFinancialId);
        headers.set("Authorization", "Basic " + password);
        headers.set("x-correlation-id", String.valueOf(correlationId));
        headers.set("x-idempotency-key", UUID.randomUUID().toString());
        headers.set("Ocp-Apim-Subscription-Key", apiKey);
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    public static HttpHeaders createRiaHeader(Long apiTraceId, String branchUser, String deviceId, String dateTime, RiaExchangePropertyDTO riaProperties) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("ria-CallDateTimeLocal", dateTime);
        headers.set("ria-CallerCorrelationId", "" + apiTraceId);
        headers.set("ria-CallerUserId", branchUser);
        headers.set("ria-CallerDeviceId", deviceId);
        headers.set("ria-AgentId", riaProperties.getAgentId());
        headers.set("Ocp-Apim-Subscription-Key", riaProperties.getOcpApimSubKey());
        headers.set("ria-ApiVersion", riaProperties.getApiVersion());
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Content-Type", "application/json");
        return headers;
    }

    public static <T> HttpEntity<T> createHttpEntity(T body, Long correlationId, String apiFinancialId, String apiKey, String password) {
        return new HttpEntity<>(body, createICHeader(correlationId, apiFinancialId, apiKey, password));
    }

    public static <T> HttpEntity<T> createHttpEntity(T body, Long correlationId, ICExchangePropertyDTO dto) {
        return new HttpEntity<>(body, createICHeader(correlationId, dto.getAgentId(), dto.getOcpApimSubKey(), dto.getPassword()));
    }

    public static boolean isNonNull(ExchangeHouseProperty property, final String key, final String agentId, final String password) {
        return Objects.nonNull(property.getExchangeCode()) && Objects.nonNull(property.getKeyLabel()) && Objects.nonNull(property.getKeyValue()) && Objects.nonNull(key) && Objects.nonNull(agentId) && Objects.nonNull(password);
    }

    public static boolean validateIfRIAPropertiesIsNotExist(RiaExchangePropertyDTO dto) {
        return Objects.nonNull(dto.getExchangeCode()) && Objects.nonNull(dto.getOcpApimSubKey()) && Objects.nonNull(dto.getAgentId()) && Objects.nonNull(dto.getApiVersion()) && Objects.nonNull(dto.getDownloadableUrl()) && Objects.nonNull(dto.getNotifyRemStatusUrl()) && Objects.nonNull(dto.getNotifyCancelReqUrl());
    }

    public static boolean validateIfRIAPropertiesIsNotExist(RiaExchangePropertyDTO dto, String url) {
        return Objects.isNull(url) || Objects.isNull(dto.getExchangeCode()) || Objects.isNull(dto.getOcpApimSubKey()) || Objects.isNull(dto.getAgentId()) || Objects.isNull(dto.getApiVersion());
    }

    public static void validateRIAExchangePropertiesBeforeProceed(RiaExchangePropertyDTO dto, String url, String message) {
        if (validateIfRIAPropertiesIsNotExist(dto, url)) {
            logger.error(message);
            throw new NullPointerException(message);
        }
    }

    public static boolean isInvalidICProperties(ICExchangePropertyDTO dto) {
        return Objects.isNull(dto.getExchangeCode()) || Objects.isNull(dto.getOcpApimSubKey()) || Objects.isNull(dto.getAgentId()) || Objects.isNull(dto.getPassword())
                || Objects.isNull(dto.getOutstandingUrl()) || Objects.isNull(dto.getStatusUrl()) || Objects.isNull(dto.getNotifyRemStatusUrl()) || Objects.isNull(dto.getPaymentReceiveUrl())
                || Objects.isNull(dto.getUnlockUrl()) || Objects.isNull(dto.getTransactionReportUrl());
    }

    public static boolean isInvalidICProperties(ICExchangePropertyDTO dto, String url) {
        return Objects.isNull(url) || Objects.isNull(dto.getExchangeCode()) || Objects.isNull(dto.getOcpApimSubKey()) || Objects.isNull(dto.getAgentId()) || Objects.isNull(dto.getPassword());
    }

    public static boolean isInValidICExchangeProperties(ICExchangePropertyDTO dto) {
        if (isInvalidICProperties(dto)) {
            logger.error("Instant Cash Scheduler aborted! Please add Instant Cash API Properties into the DB.");
            return true;
        }
        return false;
    }

    public static void validateICExchangePropertiesBeforeProceed(ICExchangePropertyDTO dto, String url, String message) {
        if (isInvalidICProperties(dto, url)) {
            logger.error(message);
            throw new RuntimeException(message);
        }
    }

    public static ICExchangePropertyDTO getICExchangeProperties() {
        List<ExchangeHouseProperty> exchangeHousePropertyList = LoadExchangeHouseProperty.getICExchangeHouseProperty();
        ICExchangePropertyDTO icDto = new ICExchangePropertyDTO();
        exchangeHousePropertyList.forEach(e -> {
            icDto.setExchangeCode(e.getExchangeCode());
            switch (e.getKeyLabel()) {
                case IC_AGENT_ID:
                    icDto.setAgentId(e.getKeyValue());
                    break;
                case IC_SUB_KEY_PRIMARY:
                    icDto.setOcpApimSubKey(e.getKeyValue());
                    break;
                case IC_OUTSTANDING_API:
                    icDto.setOutstandingUrl(e.getKeyValue());
                    break;
                case IC_CONFIRM_API:
                    icDto.setNotifyRemStatusUrl(e.getKeyValue());
                    break;
                case IC_RECEIVE_PAYMENT_API:
                    icDto.setPaymentReceiveUrl(e.getKeyValue());
                    break;
                case IC_UNLOCK_API:
                    icDto.setUnlockUrl(e.getKeyValue());
                    break;
                case IC_PAYMENT_API_AGENT_ID:
                    icDto.setPaymentAgentId(e.getKeyValue());
                    break;
                case IC_STATUS_API:
                    icDto.setStatusUrl(e.getKeyValue());
                    break;
                case IC_TRANSACTION_REPORT_API:
                    icDto.setTransactionReportUrl(e.getKeyValue());
                    break;
                case IC_USER_ID:
                    icDto.setIcUserId(e.getKeyValue());
                    break;
            }
        });
        return icDto;
    }

    public static RiaExchangePropertyDTO getRiaExchangePropertyDTO() {
        RiaExchangePropertyDTO riaProperties = new RiaExchangePropertyDTO();
        List<ExchangeHouseProperty> riaExchangeHousePropertyList = LoadExchangeHouseProperty.getRIAExchangeHouseProperty();
        for (ExchangeHouseProperty p : riaExchangeHousePropertyList) {
            riaProperties.setExchangeCode(p.getExchangeCode());
            switch (p.getKeyLabel()) {
                case RIA_API_VERSION:
                    riaProperties.setApiVersion(p.getKeyValue());
                    break;
                case RIA_AGENT_ID:
                    riaProperties.setAgentId(p.getKeyValue());
                    break;
                case RIA_DOWNLOADABLE_API:
                    riaProperties.setDownloadableUrl(p.getKeyValue());
                    break;
                case RIA_SEARCH_API:
                    riaProperties.setSearchUrl(p.getKeyValue());
                    break;
                case RIA_PAYMENT_API:
                    riaProperties.setPaymentUrl(p.getKeyValue());
                    break;
                case RIA_OCP_APIM_SUB_KEY:
                    riaProperties.setOcpApimSubKey(p.getKeyValue());
                    break;
                case RIA_CASH_PICKUP_CANCEL_API:
                    riaProperties.setCashPickUpCancelUrl(p.getKeyValue());
                    break;
                case RIA_NOTIFY_REM_STATUS_API:
                    riaProperties.setNotifyRemStatusUrl(p.getKeyValue());
                    break;
                case RIA_CANCEL_REQ_API:
                    riaProperties.setNotifyCancelReqUrl(p.getKeyValue());
                    break;
            }
        }
        return riaProperties;
    }

    public static String getICTransactionStatus(String status) {
        if (Objects.nonNull(status)) {
            if (status.equals(COMPLETED)) {
                return NEW_STATUS_Y;
            } else if (status.equals(REJECTED)) {
                return NEW_STATUS_X;
            }
        }
        return NEW_STATUS_D;
    }

    public static String getICTransactionRemarks(boolean isDuplicate, String status, String reason) {
        if (isDuplicate) return Constants.DUPLICATE_REMITTANCE;
        if (status.equals(NEW_STATUS_X)) return reason;
        if (status.equals(NEW_STATUS_Y)) return "Paid";
        if (status.equals(NEW_STATUS_D)) return "Success";
        return null;
    }

}
