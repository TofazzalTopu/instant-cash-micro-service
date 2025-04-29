package com.info.api.service.impl.ic;

import com.info.dto.constants.Constants;
import com.info.api.dto.ic.*;
import com.info.api.entity.ApiTrace;
import com.info.api.service.common.ApiTraceService;
import com.info.api.service.ic.ICTransactionReportService;
import com.info.api.util.ApiUtil;
import com.info.api.util.ParseUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.info.api.util.ObjectConverter.convertObjectToString;
import static com.info.api.util.ResponseUtil.createErrorResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class ICTransactionReportServiceImpl implements ICTransactionReportService<List<ICTransactionReportDTO>> {
    public static final Logger logger = LoggerFactory.getLogger(ICTransactionReportServiceImpl.class);

    private final RestTemplate restTemplate;
    private final ApiTraceService apiTraceService;

    @Override
    public APIResponse<List<ICTransactionReportDTO>> fetchICTransactionReport(ICExchangePropertyDTO icDTO, TransactionReportRequestBody report) {
        APIResponse<List<ICTransactionReportDTO>> apiResponse = new APIResponse<>();
        if (ApiUtil.isInvalidICProperties(icDTO, icDTO.getTransactionReportUrl())) {
            return createErrorResponse(apiResponse, Constants.EXCHANGE_HOUSE_PROPERTY_NOT_EXIST_FOR_TRANSACTION_REPORT);
        }

        String response = "";
        ApiTrace trace = apiTraceService.create(icDTO.getExchangeCode(), Constants.REQUEST_TYPE_TRANSACTION_REPORT, null);
        try {
            if (Objects.isNull(trace)) return createErrorResponse(apiResponse, Constants.ERROR_CREATING_API_TRACE);

            HttpEntity<String> httpEntity = ApiUtil.createHttpEntity("", trace.getId(), icDTO);
            String transactionReportUrl = icDTO.getTransactionReportUrl() + "?level=U&fromDate=" + report.getFromDate() + "&toDate=" + report.getToDate() + "&pageNumber=" + report.getPageNumber() + "&pageSize=" + report.getPageSize();

            ResponseEntity<ICTransactionReportResponse> responseEntity = restTemplate.exchange(transactionReportUrl, HttpMethod.GET, httpEntity, ICTransactionReportResponse.class);
            logger.info("\nAPI TraceId: {}\nAnd fetchICTransactionReport Response: \n{} ", trace.getId(), convertObjectToString(responseEntity.getBody()));
            apiResponse.setApiStatus(Constants.API_STATUS_VALID);

            List<ICTransactionReportDTO> transactionDTOArrayList = new ArrayList<>();
            Optional<ICTransactionReportResponse> bodyOptional = Optional.ofNullable(responseEntity.getBody());
            if (HttpStatus.OK.equals(responseEntity.getStatusCode()) && bodyOptional.isPresent() && Objects.nonNull(bodyOptional.get().getData()) && !bodyOptional.get().getData().isEmpty()) {
                transactionDTOArrayList = new ArrayList<>(bodyOptional.get().getData());
                apiResponse.setData(transactionDTOArrayList);
            }
            if (ParseUtil.isNotBlankAndNotEmptyList(transactionDTOArrayList)) {
                return deleteTraceAndCreateErrorResponse(apiResponse, trace);
            }
        } catch (Exception e) {
            response = e.getMessage();
            logger.error("Error in fetchICTransactionReport for TraceID: {}", trace.getId());
            createErrorResponse(apiResponse, response);
        }
        apiTraceService.saveApiTrace(trace, "", response, apiResponse.getApiStatus());
        return apiResponse;
    }

    private APIResponse<List<ICTransactionReportDTO>> deleteTraceAndCreateErrorResponse(APIResponse<List<ICTransactionReportDTO>> apiResponse, ApiTrace trace) {
        logger.info(Constants.TRACING_REMOVED_BECAUSE_NO_RECORD_FOUND_TRACE_ID, "fetchICTransactionReport()", trace.getId());
        apiResponse.setErrorMessage(Constants.NO_RECORD_FOUND);
        apiTraceService.deleteById(trace.getId());
        return apiResponse;
    }

}
