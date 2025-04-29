package com.info.api.util;

import com.info.dto.constants.Constants;
import com.info.api.dto.PaymentApiResponse;
import com.info.api.dto.ic.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseUtil {
    public static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    private ResponseUtil() {
    }

    public static <T> APIResponse<T> createErrorResponse(APIResponse<T> apiResponse, String errorMessage) {
        logger.error("errorMessage: {}", errorMessage);
        apiResponse.setErrorMessage(errorMessage);
        apiResponse.setApiStatus(Constants.API_STATUS_ERROR);
        return apiResponse;
    }

    public static PaymentApiResponse createErrorResponse(PaymentApiResponse response, String errorMessage) {
        logger.error("errorMessage: {}", errorMessage);
        response.setErrorMessage(errorMessage);
        response.setApiStatus(Constants.API_STATUS_ERROR);
        response.setPayoutStatus(null);
        return response;
    }

    public static <T> APIResponse<T> mapAPIErrorResponse(APIResponse<T> apiResponse, String referenceNo, String errorMessage) {
        logger.error("referenceNo: {}, errorMessage: {}", referenceNo, errorMessage);
        apiResponse.setErrorMessage(errorMessage);
        apiResponse.setApiStatus(Constants.API_STATUS_ERROR);
        return apiResponse;
    }
}
