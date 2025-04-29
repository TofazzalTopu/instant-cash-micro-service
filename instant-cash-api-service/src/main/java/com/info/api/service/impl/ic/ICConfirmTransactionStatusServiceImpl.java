package com.info.api.service.impl.ic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.info.dto.constants.Constants;
import com.info.api.dto.PaymentApiRequest;
import com.info.api.dto.PaymentApiResponse;
import com.info.api.entity.ApiTrace;
import com.info.api.entity.ICCashRemittanceData;
import com.info.api.entity.RemittanceData;
import com.info.api.mapper.ICConfirmDTOMapper;
import com.info.api.mapper.ICPaymentReceiveRemittanceMapper;
import com.info.api.dto.ic.ICConfirmDTO;
import com.info.api.dto.ic.ICConfirmResponseDTO;
import com.info.api.dto.ic.ICExchangePropertyDTO;
import com.info.api.dto.ic.ICOutstandingTransactionDTO;
import com.info.api.service.common.ApiTraceService;
import com.info.api.service.common.RemittanceDataService;
import com.info.api.service.ic.ICCashRemittanceDataService;
import com.info.api.service.ic.ICConfirmTransactionStatusService;
import com.info.api.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static com.info.api.entity.RemittanceData.*;
import static com.info.api.util.ObjectConverter.convertObjectToString;
import static com.info.api.util.ResponseUtil.createErrorResponse;

@Service
@RequiredArgsConstructor
public class ICConfirmTransactionStatusServiceImpl implements ICConfirmTransactionStatusService {
    public static final Logger logger = LoggerFactory.getLogger(ICConfirmTransactionStatusServiceImpl.class);

    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final ApiTraceService apiTraceService;
    private final RemittanceDataService remittanceDataService;
    private final ICCashRemittanceDataService icCashRemittanceDataService;
    private final ICPaymentReceiveRemittanceMapper icPaymentReceiveRemittanceMapper;

    @Override
    public List<RemittanceData> confirmOutstandingTransactionStatus(ICExchangePropertyDTO icDTO, List<RemittanceData> remittanceDataList) {

        if (ApiUtil.isInvalidICProperties(icDTO, icDTO.getNotifyRemStatusUrl())) {
            logger.error(Constants.EXCHANGE_HOUSE_PROPERTY_NOT_EXIST_FOR_NOTIFY_STATUS);
            return new ArrayList<>();
        }

        List<ApiTrace> apiTraceList = new ArrayList<>();
        List<RemittanceData> notifiedRemittances = new ArrayList<>();

        try {
            List<RemittanceData> rejectedDataList =
                    remittanceDataService.findAllByExchangeCodeAndMiddlewarePushAndFinalStatusAndSourceTypeOrProcessStatus(icDTO.getExchangeCode(), 0, Constants.API_SOURCE_TYPE, Arrays.asList(REJECTED))
                            .stream().distinct().collect(Collectors.toList());
            if (!rejectedDataList.isEmpty()) remittanceDataList.addAll(rejectedDataList);

            remittanceDataList.forEach(remittanceData -> {
                String request = "";
                String response = "";
                String apiStatus = Constants.API_STATUS_VALID;
                Date businessDate = remittanceData.getProcessDate();
                final ApiTrace apiTrace = apiTraceService.create(icDTO.getExchangeCode(), Constants.REQUEST_TYPE_NOTIFY_REM_STATUS, businessDate);
                try {
                    if (Objects.isNull(apiTrace)) return;

                    String newStatus = remittanceData.isDuplicate() ? NEW_STATUS_X : ApiUtil.getICTransactionStatus(remittanceData.getProcessStatus());
                    String remarks = ApiUtil.getICTransactionRemarks(remittanceData.isDuplicate(), newStatus, remittanceData.getStopPayReason());
                    ICConfirmDTO icConfirmDTO = ICConfirmDTO.builder().reference(remittanceData.getReferenceNo()).newStatus(newStatus).remarks(remarks).build();
                    request = convertObjectToString(icConfirmDTO);

                    HttpEntity<ICConfirmDTO> httpEntity = ApiUtil.createHttpEntity(icConfirmDTO, apiTrace.getId(), icDTO);
                    ResponseEntity<ICConfirmResponseDTO> responseEntity = restTemplate.postForEntity(icDTO.getNotifyRemStatusUrl(), httpEntity, ICConfirmResponseDTO.class);
                    response = convertObjectToString(responseEntity.getBody());

                    if (!remittanceData.isDuplicate() && !remittanceData.getProcessStatus().equals(REJECTED)) {
                        mapNotifiedICRemittanceData(responseEntity, remittanceData);
                        notifiedRemittances.add(remittanceData);
                    }
                } catch (Exception e) {
                    response = e.getMessage();
                    apiStatus = Constants.API_STATUS_ERROR;
                    logger.error("Error in notify for ReferenceNo: {}", remittanceData.getReferenceNo());
                }
                logger.info("\nRequest: {}, \nResponse: \n{} \n", request, response);
                apiTraceList.add(apiTraceService.buildApiTrace(apiTrace, request, response, apiStatus));
            });

            if (!notifiedRemittances.isEmpty()) remittanceDataList = remittanceDataService.saveAll(notifiedRemittances);

            if (!apiTraceList.isEmpty()) apiTraceService.saveAllApiTrace(apiTraceList);

        } catch (Exception e) {
            logger.error("\nError in notify: {}", e.getMessage());
        }

        return remittanceDataList;
    }

    @Override
    public PaymentApiResponse confirmCahTransactionPayment(PaymentApiResponse paymentApiResponse, PaymentApiRequest paymentApiRequest, ICExchangePropertyDTO icDTO) {
        paymentApiResponse.setTranNo(paymentApiRequest.getTranNo());

        if (ApiUtil.isInvalidICProperties(icDTO, icDTO.getNotifyRemStatusUrl())) {
            return createErrorResponse(paymentApiResponse, Constants.EXCHANGE_HOUSE_PROPERTY_NOT_EXIST_FOR_NOTIFY_STATUS);
        }
        try {
            Optional<ApiTrace> apiTraceOptional = apiTraceService.findByTranNo(paymentApiRequest.getTranNo(), Constants.REQUEST_TYPE_SEARCH);
            if (apiTraceOptional.isPresent()) {
                RemittanceData remittanceData = icPaymentReceiveRemittanceMapper.prepareICCashRemittanceData(getICOutstandingTransactionDTO(apiTraceOptional.get()), paymentApiRequest, icDTO.getExchangeCode(), apiTraceOptional.get());

                ICConfirmDTO icConfirmDTO = ICConfirmDTOMapper.mapICConfirmDTO(paymentApiRequest, NEW_STATUS_Y, "Paid");

                paymentApiResponse = notifyPaymentStatus(paymentApiResponse, icDTO, remittanceData, icConfirmDTO, paymentApiRequest.getPinno(), true);
                Optional<ICCashRemittanceData> optionalICCashRemittanceData = icCashRemittanceDataService.findByExchangeCodeAndReferenceNo(icDTO.getExchangeCode(), paymentApiRequest.getPinno());
                optionalICCashRemittanceData.ifPresent(icCashRemittanceDataService::delete);
                paymentApiResponse.setTransRefID(remittanceData.getExchangeTransactionNo());
            } else {
                paymentApiResponse.setErrorMessage(Constants.TRAN_NO_NOT_EXIST);
                paymentApiResponse.setApiStatus(Constants.API_STATUS_INVALID);
                logger.info("Error in confirmCahTransactionPayment for ReferenceNo: {},  ERROR: {}", paymentApiRequest.getTranNo(), Constants.TRAN_NO_NOT_EXIST);
            }
        } catch (Exception e) {
            handleException(paymentApiResponse, paymentApiRequest.getPinno(), e);
        }
        return paymentApiResponse;
    }

    @Override //send acknowledge notification to IC after receive payment
    public PaymentApiResponse notifyPaymentStatus(PaymentApiResponse paymentApiResponse, ICExchangePropertyDTO icDTO, RemittanceData remittanceData, ICConfirmDTO icConfirmDTO, String referenceNo, boolean update) {

        final ApiTrace apiTrace = apiTraceService.create(icDTO.getExchangeCode(), Constants.REQUEST_TYPE_NOTIFY_REM_STATUS, remittanceData.getProcessDate());
        try {
            if (apiTrace == null) return createErrorResponse(paymentApiResponse, Constants.ERROR_CREATING_API_TRACE);

            paymentApiResponse.setOriginalRequest(convertObjectToString(icConfirmDTO));

            logRequest(paymentApiResponse.getOriginalRequest());

            ResponseEntity<ICConfirmResponseDTO> responseEntity = sendNotifyRemStatusRequest(icDTO, icConfirmDTO, apiTrace);
            paymentApiResponse.setPayoutStatus(responseEntity.getStatusCode().toString());
            logResponse(responseEntity.getBody());

            String status = processResponse(responseEntity, remittanceData, update);
            paymentApiResponse.setApiStatus(status);
        } catch (Exception e) {
            handleException(paymentApiResponse, referenceNo, e);
        }
        apiTraceService.saveApiTrace(apiTrace, paymentApiResponse.getOriginalRequest(), remittanceData.getApiResponse(), paymentApiResponse.getApiStatus());
        return paymentApiResponse;
    }

    private ICOutstandingTransactionDTO getICOutstandingTransactionDTO(ApiTrace apiTrace) throws JsonProcessingException {
        return mapper.readValue(apiTrace.getResponseMsg(), ICOutstandingTransactionDTO.class);
    }

    private void mapNotifiedICRemittanceData(ResponseEntity<ICConfirmResponseDTO> responseEntity, RemittanceData remittanceData) {
        remittanceData.setApiResponse(convertObjectToString(responseEntity.getBody()));
        if (HttpStatus.OK.equals(responseEntity.getStatusCode()) && Objects.nonNull(responseEntity.getBody())) {
            logger.info("\nNotify Response data: {} ", responseEntity.getBody());
            remittanceData.setMiddlewarePush(Constants.MIDDLEWARE_PUSH_DONE);
        } else {
            if (remittanceData.getTryCount() >= Constants.TRY_COUNT) {
                remittanceData.setMiddlewarePush(Constants.MIDDLEWARE_PUSH_ERROR);
            } else {
                remittanceData.setTryCount(remittanceData.getTryCount() + 1);
            }
            logger.error("updateNotifiedICRemittanceData() API Responded with different status code: {}", responseEntity.getStatusCode());
        }
    }

    private ResponseEntity<ICConfirmResponseDTO> sendNotifyRemStatusRequest(ICExchangePropertyDTO icDTO, ICConfirmDTO icConfirmDTO, ApiTrace apiTrace) {
        HttpEntity<ICConfirmDTO> httpEntity = ApiUtil.createHttpEntity(icConfirmDTO, apiTrace.getId(), icDTO);
        return restTemplate.postForEntity(icDTO.getNotifyRemStatusUrl(), httpEntity, ICConfirmResponseDTO.class);
    }

    private String processResponse(ResponseEntity<ICConfirmResponseDTO> responseEntity, RemittanceData remittanceData, boolean update) {
        if (HttpStatus.OK.equals(responseEntity.getStatusCode()) && responseEntity.hasBody()) {
            remittanceData.setApiResponse(convertObjectToString(responseEntity.getBody()));
            remittanceData.setMiddlewarePush(Constants.MIDDLEWARE_PUSH_DONE);
            if (update) remittanceDataService.save(remittanceData);
            return Constants.API_STATUS_VALID;
        }
        return Constants.API_STATUS_INVALID;
    }

    private void handleException(PaymentApiResponse response, String referenceNo, Exception e) {
        response.setErrorMessage(e.getMessage());
        response.setApiStatus(Constants.API_STATUS_ERROR);
        logger.error("Error in notify for ReferenceNo: {}, ERROR: {}", referenceNo, e.getMessage());
    }

    private void logResponse(ICConfirmResponseDTO responseBody) {
        logger.info("\nnotifyPaymentStatus Response: {}", convertObjectToString(responseBody));
    }

    private void logRequest(String request) {
        logger.info("\nnotifyPaymentStatus Request: {}", request);
    }

}

