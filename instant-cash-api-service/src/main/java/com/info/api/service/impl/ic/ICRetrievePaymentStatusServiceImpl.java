package com.info.api.service.impl.ic;

import com.info.api.constants.Constants;
import com.info.api.dto.ic.APIResponse;
import com.info.api.dto.ic.ICExchangePropertyDTO;
import com.info.api.dto.ic.ICPaymentStatusDTO;
import com.info.api.entity.ApiTrace;
import com.info.api.service.common.ApiTraceService;
import com.info.api.service.ic.ICRetrievePaymentStatusService;
import com.info.api.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static com.info.api.util.ObjectConverter.convertObjectToString;
import static com.info.api.util.ResponseUtil.createErrorResponse;
import static com.info.api.util.ResponseUtil.mapAPIErrorResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class ICRetrievePaymentStatusServiceImpl implements ICRetrievePaymentStatusService<ICPaymentStatusDTO> {
    public static final Logger logger = LoggerFactory.getLogger(ICRetrievePaymentStatusServiceImpl.class);

    private final RestTemplate restTemplate;
    private final ApiTraceService apiTraceService;

    @Override
    public APIResponse<ICPaymentStatusDTO> getPaymentStatus(ICExchangePropertyDTO dto, String referenceNo) {
        String response = "";
        APIResponse<ICPaymentStatusDTO> apiResponse = new APIResponse<>();

        if (ApiUtil.isInvalidICProperties(dto, dto.getStatusUrl())) {
            return mapAPIErrorResponse(apiResponse, referenceNo, Constants.EXCHANGE_HOUSE_PROPERTY_NOT_EXIST_FOR_RETRIEVE_PAYMENT_STATUS);
        }

        final ApiTrace apiTrace = apiTraceService.create(dto.getExchangeCode(), Constants.RETRIEVE_PAYMENT_STATUS, null);
        try {
            if (Objects.isNull(apiTrace)) return createErrorResponse(apiResponse, Constants.ERROR_CREATING_API_TRACE);
            logger.info("\ngetPaymentStatus remittance for referenceNo={}, exchangeCode={}, apiTraceID: {}", referenceNo, dto.getExchangeCode(), apiTrace.getId());

            String paymentStatusUrl = dto.getStatusUrl() + "?reference=" + referenceNo;
            logger.info("getPaymentStatus paymentStatusUrl GET: {}", paymentStatusUrl);
            ResponseEntity<ICPaymentStatusDTO> responseEntity = restTemplate.exchange(paymentStatusUrl, HttpMethod.GET,
                    ApiUtil.createHttpEntity("", apiTrace.getId(), dto), ICPaymentStatusDTO.class);
            logger.info("\ngetPaymentStatus code: {}, Response: {}", responseEntity.getStatusCodeValue(), responseEntity.getBody());

            if ((responseEntity.getStatusCode().equals(HttpStatus.OK)) && responseEntity.hasBody()) {
                ICPaymentStatusDTO icPaymentStatusDTO = responseEntity.getBody();
                logger.info("\ngetPaymentStatus Response data: {}", responseEntity.getBody());
                response = convertObjectToString(icPaymentStatusDTO);
                apiResponse.setApiStatus(Constants.API_STATUS_VALID);
                apiResponse.setData(icPaymentStatusDTO);
            }
        } catch (HttpStatusCodeException e) {
            response = e.getMessage();
            mapAPIErrorResponse(apiResponse, referenceNo, e.getResponseBodyAsString());
        } catch (Exception e) {
            response = e.getMessage();
            mapAPIErrorResponse(apiResponse, referenceNo, e.getMessage());
        }
        apiTraceService.saveApiTrace(apiTrace, referenceNo, response, apiResponse.getApiStatus());

        return apiResponse;
    }


}
