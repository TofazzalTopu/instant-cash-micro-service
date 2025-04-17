package com.info.api.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.info.api.constants.Constants;
import com.info.api.dto.PaymentApiRequest;
import com.info.api.dto.PaymentApiResponse;
import com.info.api.dto.ic.LoginErrorResponse;
import com.info.api.dto.ic.TransactionReportRequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ParseUtil {

    private ParseUtil() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ParseUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    public static PaymentApiRequest parseAndPrepareRequest(String data, String requestIp) {
        PaymentApiRequest paymentApiRequest;
        try {
            paymentApiRequest = objectMapper.readValue(data, PaymentApiRequest.class);
            paymentApiRequest.setIpAddress(requestIp);
        } catch (Exception e) {
            logger.error("Error in parseAndPrepareRequest: Error = {}", e.getMessage(), e);
            paymentApiRequest = new PaymentApiRequest();
            paymentApiRequest.setIpAddress(requestIp);
        }
        return paymentApiRequest;
    }

    public static PaymentApiResponse mapPaymentApiResponse(PaymentApiResponse paymentApiResponse, PaymentApiRequest paymentApiRequest) {
        paymentApiResponse.setPayoutStatus(null);

        if (Objects.isNull(paymentApiRequest)) {
            paymentApiResponse.setErrorMessage("Request Can not be empty");
        } else if (isNullOrEmpty(paymentApiRequest.getExchCode())) {
            paymentApiResponse.setErrorMessage("Exchange Code can not be null or empty");
        } else if (isNullOrEmpty(paymentApiRequest.getPinno())) {
            paymentApiResponse.setErrorMessage("Pin No can not be null or empty");
        } else if (isNullOrEmpty(paymentApiRequest.getBrUserId())) {
            paymentApiResponse.setErrorMessage("Branch User ID can not be null or empty");
        } else if (isNullOrEmpty(paymentApiRequest.getBrCode())) {
            paymentApiResponse.setErrorMessage("Branch Code can not be null or empty");
        } else if (isNullOrEmpty(paymentApiRequest.getBeneIDNumber())) {
            paymentApiResponse.setErrorMessage("Beneficiary ID can not be null or empty");
        } else if (isNullOrEmpty(paymentApiRequest.getDob())) {
            paymentApiResponse.setErrorMessage("Date of Birth can not be null or empty");
        } else if (isNullOrEmpty(paymentApiRequest.getTranNo())) {
            paymentApiResponse.setErrorMessage("Tran No can not be null or empty");
        } else if (isNullOrEmpty(paymentApiRequest.getAddress())) {
            paymentApiResponse.setErrorMessage("Address can not be null or empty");
        } else if (isNullOrEmpty(paymentApiRequest.getCity())) {
            paymentApiResponse.setErrorMessage("City can not be null or empty");
        } else if (isNullOrEmpty(paymentApiRequest.getMobileNo())) {
            paymentApiResponse.setErrorMessage("Mobile No can not be null or empty");
        } else if (isNullOrEmpty(paymentApiRequest.getPurposeOfTran())) {
            paymentApiResponse.setErrorMessage("Purpose of Transaction can not be null or empty");
        } else if (isNullOrEmpty(paymentApiRequest.getRelationWithRemitter())) {
            paymentApiResponse.setErrorMessage("Relation with remitter can not be null or empty");
        }
        paymentApiResponse.setApiStatus(Constants.API_STATUS_INVALID);
        return paymentApiResponse;
    }

    public static boolean isValidPaymentRequest(PaymentApiRequest paymentApiRequest) {
        return Objects.nonNull(paymentApiRequest)
                && isNotBlankAndNotEmpty(paymentApiRequest.getExchCode())
                && isNotBlankAndNotEmpty(paymentApiRequest.getPinno())
                && isNotBlankAndNotEmpty(paymentApiRequest.getBrUserId())
                && isNotBlankAndNotEmpty(paymentApiRequest.getBrCode())
                && isNotBlankAndNotEmpty(paymentApiRequest.getBeneIDNumber())
                && isNotBlankAndNotEmpty(paymentApiRequest.getDob())
                && isNotBlankAndNotEmpty(paymentApiRequest.getTranNo())
                && isNotBlankAndNotEmpty(paymentApiRequest.getAddress())
                && isNotBlankAndNotEmpty(paymentApiRequest.getCity())
                //&& isNotBlankAndNotEmpty(paymentApiRequest.getZipCode())
                && isNotBlankAndNotEmpty(paymentApiRequest.getMobileNo())
                && isNotBlankAndNotEmpty(paymentApiRequest.getPurposeOfTran())
                && isNotBlankAndNotEmpty(paymentApiRequest.getRelationWithRemitter());
    }

    public static boolean isValidICTransactionReportBody(TransactionReportRequestBody report) {
        return isNotBlankAndNotEmpty(report.getUserId()) && isNotBlankAndNotEmpty(report.getPassword()) &&
                isNotBlankAndNotEmpty(report.getExchcode()) && isNotBlankAndNotEmpty(report.getFromDate()) && isNotBlankAndNotEmpty(report.getToDate());
    }


    public static boolean isNotBlankAndNotEmpty(String value) {
        return Objects.nonNull(value) && !"".equals(value) && !value.isEmpty();
    }

    public static boolean isNotBlankAndNotEmpty(Object value) {
        return Objects.nonNull(value) && !"".equals(value) && !value.toString().isEmpty();
    }

    public static boolean isNotBlankAndNotEmptyList(List<? extends Object> list) {
        return Objects.nonNull(list) && !list.isEmpty();
    }

    public static boolean isNullOrEmpty(String value) {
        return Objects.isNull(value) || value.isEmpty();
    }

    public static boolean isNullOrEmpty(String... values) {
        return Arrays.stream(values).anyMatch(v -> v == null || v.isEmpty());
    }

    public static PaymentApiResponse unAuthorized(PaymentApiResponse paymentApiResponse) {
        paymentApiResponse.setPayoutStatus(null);
        paymentApiResponse.setErrorMessage(Constants.UNAUTHORIZED_ACCESS);
        paymentApiResponse.setApiStatus(Constants.API_STATUS_INVALID);
        return paymentApiResponse;
    }

    public static LoginErrorResponse unAuthorized() {
        return LoginErrorResponse.builder().payoutStatus(null).apiStatus(Constants.API_STATUS_INVALID).errorMessage(Constants.UNAUTHORIZED_ACCESS).build();
    }

}
