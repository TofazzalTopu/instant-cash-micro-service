package com.info.api.service.impl.ic;

import com.info.dto.constants.Constants;
import com.info.api.dto.ic.APIResponse;
import com.info.api.dto.ic.ICExchangePropertyDTO;
import com.info.api.dto.ic.ICUnlockTransactionRequestDTO;
import com.info.api.dto.ic.ICUnlockTransactionResponseDTO;
import com.info.api.entity.ApiTrace;
import com.info.api.entity.RemittanceData;
import com.info.api.service.common.ApiTraceService;
import com.info.api.service.common.RemittanceDataService;
import com.info.api.service.ic.ICUnlockRemittanceService;
import com.info.api.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static com.info.api.util.ObjectConverter.convertObjectToString;
import static com.info.api.util.ObjectConverter.convertStringToObject;
import static com.info.api.util.PasswordUtil.generateBase64Hash;
import static com.info.api.util.ResponseUtil.createErrorResponse;


@Service
@Transactional
@RequiredArgsConstructor
public class ICUnlockRemittanceServiceImpl implements ICUnlockRemittanceService {

    public static final Logger logger = LoggerFactory.getLogger(ICUnlockRemittanceServiceImpl.class);

    private final RestTemplate restTemplate;
    private final ApiTraceService apiTraceService;
    private final RemittanceDataService remittanceDataService;

    @Value("${INSTANT_CASH_API_USER_PASSWORD}")
    String icPassword;

    @Override
    public APIResponse<String> unlockICOutstandingRemittance(String referenceNo, ICExchangePropertyDTO dto) {
        String response = "";
        APIResponse<String> apiResponse = new APIResponse<>();
        dto.setPassword(icPassword);

        if (ApiUtil.isInvalidICProperties(dto, dto.getUnlockUrl())) {
            return createErrorResponse(apiResponse, Constants.EXCHANGE_HOUSE_PROPERTY_NOT_EXIST_FOR_UNLOCK_REMITTANCE);
        }
        Optional<RemittanceData> remittanceDataOptional = remittanceDataService.findByExchangeCodeAndReferenceNoAndProcessStatusesIsNot(dto.getExchangeCode(), referenceNo, Arrays.asList(RemittanceData.COMPLETED, RemittanceData.UNLOCK));
        if (remittanceDataOptional.isEmpty()) return createErrorResponse(apiResponse, Constants.REFERENCE_NOT_EXIST);

        final ApiTrace apiTrace = apiTraceService.create(dto.getExchangeCode(), Constants.UNLOCK_TRANSACTION, null);
        if (Objects.isNull(apiTrace)) return createErrorResponse(apiResponse, Constants.ERROR_CREATING_API_TRACE);

        try {
            ICUnlockTransactionRequestDTO icUnlockTransactionRequestDTO = new ICUnlockTransactionRequestDTO(referenceNo);
            HttpEntity<ICUnlockTransactionRequestDTO> httpEntity = ApiUtil.createHttpEntity(icUnlockTransactionRequestDTO, apiTrace.getId(), dto);
            logger.info("unlockICOutstandingRemittance request: {}", httpEntity);

            ResponseEntity<ICUnlockTransactionResponseDTO> responseEntity = restTemplate.postForEntity(dto.getUnlockUrl(), httpEntity, ICUnlockTransactionResponseDTO.class);

            if ((responseEntity.getStatusCode().equals(HttpStatus.OK)) && Objects.nonNull(responseEntity.getBody())) {
                remittanceDataOptional.get().setProcessStatus(RemittanceData.UNLOCK);
                remittanceDataService.save(remittanceDataOptional.get());
                apiTrace.setStatus(Constants.API_STATUS_VALID);
                response = convertObjectToString(responseEntity.getBody());
            } else {
                apiTraceService.deleteById(apiTrace.getId());
                logger.info(Constants.TRACING_REMOVED_BECAUSE_NO_RECORD_FOUND_TRACE_ID, "unlockICOutstandingRemittance()", apiTrace.getId());
            }
        } catch (HttpStatusCodeException e) {
            apiTrace.setStatus(Constants.API_STATUS_ERROR);
            response = updateRemittanceAndMapErrorResponse(e, remittanceDataOptional.get());
        } catch (Exception e) {
            response = e.getMessage();
            apiTrace.setStatus(Constants.API_STATUS_ERROR);
            logger.error("Error in unlockICOutstandingRemittance for TraceID: {}, Message: {}", apiTrace.getId(), e.getMessage());
        }
        apiTraceService.saveApiTrace(apiTrace, referenceNo, response, apiTrace.getStatus());
        apiResponse.setData(response);
        apiResponse.setApiStatus(apiTrace.getStatus());
        if(!apiTrace.getStatus().equals(Constants.API_STATUS_VALID)) apiResponse.setErrorMessage(response);
        return apiResponse;
    }

    private String updateRemittanceAndMapErrorResponse(HttpStatusCodeException e, RemittanceData remittanceData) {
        logger.error("HTTP error: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
        updateTryCountToProcessUnlockRemittance(remittanceData);
        return convertObjectToString(convertStringToObject(e.getResponseBodyAsString(), ICUnlockTransactionResponseDTO.class));
    }

    private void processUnlockRemittance(ResponseEntity<String> responseEntity, RemittanceData remittanceData) {
        String responseData = "";
        try {
            if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                if (Objects.nonNull(responseEntity.getBody())) {
                    logger.info("\nConfirmation Response data: {}", responseEntity.getBody());

                    remittanceData.setApiResponse(responseEntity.getBody());
                    remittanceData.setMiddlewarePush(Constants.MIDDLEWARE_PUSH_DONE);
                } else {
                    remittanceData.setMiddlewarePush(Constants.MIDDLEWARE_PUSH_ERROR);
                    logger.error("Response body contains empty response against: {} ", remittanceData.getReferenceNo());
                }
            } else {
                logger.error("API Responded with different status code: {} ", responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("Error in processConfirmRemittanceData(). Error = {}", e.getMessage());
            logger.info("\nAPI request data is --> {} ", responseData);
        }
    }

    private void updateTryCountToProcessUnlockRemittance(RemittanceData remittanceData) {
        remittanceData.setProcessStatus(RemittanceData.UNLOCK);
        remittanceData.setTryCount(remittanceData.getTryCount() + 1);
        remittanceData.setMiddlewarePush(Constants.MIDDLEWARE_PUSH_ERROR);
        remittanceDataService.save(remittanceData);
    }

}
