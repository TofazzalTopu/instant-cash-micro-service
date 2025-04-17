package com.info.api.mapper;

import com.info.api.constants.Constants;
import com.info.api.dto.PaymentApiRequest;
import com.info.api.dto.SearchApiResponse;
import com.info.api.entity.ApiTrace;
import com.info.api.entity.ICCashRemittanceData;
import com.info.api.entity.RemittanceData;
import com.info.api.dto.ic.ICBankDetailsDTO;
import com.info.api.dto.ic.ICOutstandingTransactionDTO;
import com.info.api.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Component
public class ICPaymentReceiveRemittanceMapper {

    public static final Logger logger = LoggerFactory.getLogger(ICPaymentReceiveRemittanceMapper.class);

    public RemittanceData prepareICCashRemittanceData(ICOutstandingTransactionDTO icOutstandingDTO, String exchangeCode, ApiTrace trace) {
        RemittanceData remittanceData = new RemittanceData();
        try {
            DateFormat dt = new SimpleDateFormat("yyyyMMdd");
            remittanceData.setAmount(Objects.nonNull(icOutstandingDTO.getPayingAmount()) ? icOutstandingDTO.getPayingAmount() : icOutstandingDTO.getSettlementAmount());
            remittanceData.setAmountOriginate(icOutstandingDTO.getSettlementAmount());
            remittanceData.setPaidBy(icOutstandingDTO.getPayingAgentName());
            remittanceData.setCharges(icOutstandingDTO.getCommissionAmount());
            remittanceData.setPaidBranch(icOutstandingDTO.getMessagePayeeBranch());

            mapBeneficiaryInfo(remittanceData, icOutstandingDTO);

            remittanceData.setExchangeCode(exchangeCode);
            remittanceData.setExchangeName(Constants.EXCHANGE_HOUSE_INSTANT_CASH);
            remittanceData.setExchangeTransactionDate(icOutstandingDTO.getSentAt());
            remittanceData.setExchangeTransactionNo(icOutstandingDTO.getPartnerReference());
            remittanceData.setCountryOriginate(icOutstandingDTO.getOriginatingCountry());
            remittanceData.setPurpose(icOutstandingDTO.getRemittancePurpose());
            remittanceData.setReferenceDate(dt.parse(icOutstandingDTO.getSentAt()));
            remittanceData.setReferenceNo(icOutstandingDTO.getReference());
            remittanceData.setSecurityCode(icOutstandingDTO.getPartnerReference()); // data not match
            remittanceData.setCurrencyOriginate(icOutstandingDTO.getPayingCurrency());
            remittanceData.setExchangeRate(icOutstandingDTO.getSettlementRate());

            mapRemitterInfo(remittanceData, icOutstandingDTO);
            mapBankBranchInfo(remittanceData, icOutstandingDTO);

            remittanceData.setProcessStatus(RemittanceData.OPEN);
//            remittanceData.setFinalStatus("02");
            remittanceData.setProcessDate(trace.getCbsDate());
            remittanceData.setMiddlewareId(trace.getId());
            remittanceData.setMiddlewarePush(Constants.MIDDLEWARE_PUSH_UNDONE);
            remittanceData.setRemittanceMessageType("WEB");
            remittanceData.setSourceType(Constants.API_SOURCE_TYPE);
        } catch (Exception e) {
            logger.error("Error in ICCashRemittance prepareRemittanceData for TraceID: " + trace.getId(), e);
        }
        return remittanceData;
    }

    public RemittanceData prepareICCashRemittanceData(ICOutstandingTransactionDTO icOutstandingDTO, PaymentApiRequest paymentApiRequest, String exchangeCode, ApiTrace trace) {
        RemittanceData remittanceData = prepareICCashRemittanceData(icOutstandingDTO, exchangeCode, trace);
        remittanceData.setExchangeCode(paymentApiRequest.getExchCode());
        remittanceData.setBranchCode(Integer.parseInt(paymentApiRequest.getBrCode()));
        remittanceData.setReferenceNo(paymentApiRequest.getPinno());
        remittanceData.setIdNo(paymentApiRequest.getBeneIDNumber());
        remittanceData.setReceiverAddress(paymentApiRequest.getAddress());
        remittanceData.setPhoneNo(paymentApiRequest.getMobileNo());
        remittanceData.setSenderPhone(paymentApiRequest.getMobileNo());
        remittanceData.setCityDistrict(paymentApiRequest.getCity());
        remittanceData.setCountryOriginate(paymentApiRequest.getBeneIDIssuedByCountry());
        return remittanceData;
    }

    private void mapBeneficiaryInfo(RemittanceData remittanceData, ICOutstandingTransactionDTO icOutstandingDTO) {
        try {
            if (Objects.nonNull(icOutstandingDTO.getBeneficiary())) {
                String beneficiaryFirstName = Objects.nonNull(icOutstandingDTO.getBeneficiary().getFirstName()) ? icOutstandingDTO.getBeneficiary().getFirstName() : "";
                String beneficiaryMiddleName = Objects.nonNull(icOutstandingDTO.getBeneficiary().getMiddleName()) ? icOutstandingDTO.getBeneficiary().getMiddleName() : "";
                String beneficiaryLastName = Objects.nonNull(icOutstandingDTO.getBeneficiary().getLastName()) ? icOutstandingDTO.getBeneficiary().getLastName() : "";

                remittanceData.setCreditorName(beneficiaryFirstName + " " + beneficiaryMiddleName + " " + beneficiaryLastName);
                String beneficiaryPhoneNo = Objects.nonNull(icOutstandingDTO.getBeneficiary().getPhoneNumber()) ? icOutstandingDTO.getBeneficiary().getPhoneNumber() : icOutstandingDTO.getBeneficiary().getMobileNumber();
                remittanceData.setPhoneNo(beneficiaryPhoneNo);

                if (Objects.nonNull(icOutstandingDTO.getBeneficiary().getAddress())) {
                    String city = Objects.nonNull(icOutstandingDTO.getBeneficiary().getAddress().getCity()) ? icOutstandingDTO.getBeneficiary().getAddress().getCity() : "";
                    String district = Objects.nonNull(icOutstandingDTO.getBeneficiary().getAddress().getDistrict()) ? icOutstandingDTO.getBeneficiary().getAddress().getDistrict() : "";
                    remittanceData.setCityDistrict(city + " " + district);
                    String address1 = Objects.nonNull(icOutstandingDTO.getBeneficiary().getAddress().getAddressLine1()) ? icOutstandingDTO.getBeneficiary().getAddress().getAddressLine1() : "";
                    String address2 = Objects.nonNull(icOutstandingDTO.getBeneficiary().getAddress().getAddressLine2()) ? icOutstandingDTO.getBeneficiary().getAddress().getAddressLine2() : "";
                    remittanceData.setReceiverAddress(address1 + " " + address2);
                }
            }
        } catch (Exception e) {
            logger.error("Error in mapBeneficiaryInfo()", e);
        }
    }

    private void mapRemitterInfo(RemittanceData remittanceData, ICOutstandingTransactionDTO icOutstandingDTO) {
        if (Objects.nonNull(icOutstandingDTO.getRemitter())) {
            remittanceData.setSenderPhone(Objects.nonNull(icOutstandingDTO.getRemitter().getPhoneNumber()) ? icOutstandingDTO.getRemitter().getPhoneNumber() : icOutstandingDTO.getRemitter().getMobileNumber());
            String senderFirstName = Objects.nonNull(icOutstandingDTO.getRemitter().getFirstName()) ? icOutstandingDTO.getRemitter().getFirstName() : "";
            String senderMiddleName = Objects.nonNull(icOutstandingDTO.getRemitter().getMiddleName()) ? icOutstandingDTO.getRemitter().getMiddleName() : "";
            String senderLastName = Objects.nonNull(icOutstandingDTO.getRemitter().getLastName()) ? icOutstandingDTO.getRemitter().getLastName() : "";
            remittanceData.setSenderName(senderFirstName + " " + senderMiddleName + " " + senderLastName);

            if (Objects.nonNull(icOutstandingDTO.getRemitter().getAddress())) {
                String address1 = Objects.nonNull(icOutstandingDTO.getRemitter().getAddress().getAddressLine1()) ? icOutstandingDTO.getRemitter().getAddress().getAddressLine1() : "";
                String address2 = Objects.nonNull(icOutstandingDTO.getRemitter().getAddress().getAddressLine2()) ? icOutstandingDTO.getRemitter().getAddress().getAddressLine2() : "";
                remittanceData.setSenderAddress(address1 + " " + address2);
            }

            if (Objects.nonNull(icOutstandingDTO.getRemitter().getIdDetails())) {
                remittanceData.setIdNo(icOutstandingDTO.getRemitter().getIdDetails().getNumber());
                remittanceData.setSenderIdType(icOutstandingDTO.getRemitter().getIdDetails().getType());
            }
        }
    }

    // if the first 3 digits of the bankCode is 185 (RUPALI Bank Head Office) then the transaction type is EFT.
    private void mapBankBranchInfo(RemittanceData remittanceData, ICOutstandingTransactionDTO icOutstandingDTO) {
        try {
            boolean isBankDetailsNonEmpty = Objects.nonNull(icOutstandingDTO.getBeneficiary()) && Objects.nonNull(icOutstandingDTO.getBeneficiary().getBankDetails());
            ICBankDetailsDTO icBankDetailsDTO = icOutstandingDTO.getBeneficiary().getBankDetails();
            if (isBankDetailsNonEmpty) {
                String routingNumber = icBankDetailsDTO.getBankCode();
                remittanceData.setBranchRoutingNumber(routingNumber);

                String address1 = Objects.nonNull(icBankDetailsDTO.getBankAddress1()) ? icBankDetailsDTO.getBankAddress1() : "";
                String address2 = Objects.nonNull(icBankDetailsDTO.getBankAddress2()) ? icBankDetailsDTO.getBankAddress2() : "";
                remittanceData.setBankName(icBankDetailsDTO.getBankName());
                remittanceData.setBranchName(icBankDetailsDTO.getBankName() + ", " + address1 + ", " + address2);
            }
        } catch (Exception e) {
            logger.error("Error in ICCashRemittance mapBankBranchInfo()", e);
        }
    }

    public void mapSearchApiResponse(SearchApiResponse searchApiResponse, ICCashRemittanceData icCashRemittanceData) {
        searchApiResponse.setExchTranId(icCashRemittanceData.getExchangeTransactionNo());
        searchApiResponse.setExchCode(icCashRemittanceData.getExchangeCode());
        searchApiResponse.setOrignCountryName(icCashRemittanceData.getCountryOriginate());
        searchApiResponse.setPinno(icCashRemittanceData.getReferenceNo());
        searchApiResponse.setReference(icCashRemittanceData.getReferenceNo());
        searchApiResponse.setFxAmount(String.valueOf(icCashRemittanceData.getAmount()));
        searchApiResponse.setBenfFirstName(icCashRemittanceData.getCreditorName());
        searchApiResponse.setRemitterName(icCashRemittanceData.getSenderName());
        searchApiResponse.setOriginalCurrency(icCashRemittanceData.getCurrencyOriginate());
        searchApiResponse.setOriginalAmount(String.valueOf(icCashRemittanceData.getAmountOriginate()));
        searchApiResponse.setPayoutStatusDetails(icCashRemittanceData.getProcessStatus());
        searchApiResponse.setTranNo(icCashRemittanceData.getExchangeTransactionNo());
        searchApiResponse.setOriginalRequest(icCashRemittanceData.getReferenceNo());
        String tranDate = String.valueOf(icCashRemittanceData.getProcessDate());

        try {
            if (Objects.nonNull(tranDate)) {
                DateFormat df = new SimpleDateFormat("yyyyMMdd");
                tranDate = DateUtil.convertCalendarToString(df.getCalendar(), "yyyy-MM-dd");
                searchApiResponse.setTranDate(tranDate);
            }
        } catch (Exception e) {
            searchApiResponse.setTranDate(tranDate);
        }
    }

    public SearchApiResponse createErrorResponse(SearchApiResponse response, String errorMessage) {
        logger.error(errorMessage);
        response.setErrorMessage(errorMessage);
        response.setApiStatus(Constants.API_STATUS_ERROR);
        return response;
    }
}
