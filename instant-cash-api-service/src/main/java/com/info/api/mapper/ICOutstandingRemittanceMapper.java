package com.info.api.mapper;

import com.info.dto.constants.Constants;
import com.info.api.dto.ic.ICBankDetailsDTO;
import com.info.api.dto.ic.ICOutstandingTransactionDTO;
import com.info.api.entity.ApiTrace;
import com.info.api.entity.Branch;
import com.info.api.entity.MbkBrn;
import com.info.api.entity.RemittanceData;
import com.info.api.util.RemittanceValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import static com.info.api.util.ParseUtil.isNotBlankAndNotEmpty;

@Component
public class ICOutstandingRemittanceMapper {

    public static final Logger logger = LoggerFactory.getLogger(ICOutstandingRemittanceMapper.class);

    @Value("${bank.code}")
    private String bankCode;


    public RemittanceData prepareRemittanceData(ICOutstandingTransactionDTO icOutstandingDTO, String exchangeCode, ApiTrace trace, Map<Integer, Branch> branchMap, Map<String, MbkBrn> mbkBrnMap) {
        RemittanceData remittanceData = new RemittanceData();
        try {
            DateFormat dt = new SimpleDateFormat("yyyyMMdd");
            if (icOutstandingDTO.isDuplicate()) {
                remittanceData.setDuplicate(true);
                remittanceData.setProcessStatus(RemittanceData.REJECTED);
            } else {
                remittanceData.setProcessStatus(RemittanceData.OPEN);
            }

            remittanceData.setAmount(isNotBlankAndNotEmpty(icOutstandingDTO.getPayingAmount()) ? icOutstandingDTO.getPayingAmount() : icOutstandingDTO.getSettlementAmount());
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
            mapBankBranchInfo(remittanceData, icOutstandingDTO, branchMap, mbkBrnMap);

            remittanceData.setProcessDate(trace.getCbsDate());
            remittanceData.setMiddlewareId(trace.getId());
            remittanceData.setMiddlewarePush(Constants.MIDDLEWARE_PUSH_UNDONE);
            remittanceData.setSourceType(Constants.API_SOURCE_TYPE);
        } catch (Exception e) {
            logger.error("Error in prepareRemittanceData for TraceID: " + trace.getId(), e);
        }
        return remittanceData;
    }

    private void mapBeneficiaryInfo(RemittanceData remittanceData, ICOutstandingTransactionDTO icOutstandingDTO) {
        try {
            if (isNotBlankAndNotEmpty(icOutstandingDTO.getBeneficiary())) {
                String beneficiaryFirstName = Objects.nonNull(icOutstandingDTO.getBeneficiary().getFirstName()) ? icOutstandingDTO.getBeneficiary().getFirstName() : "";
                String beneficiaryMiddleName = Objects.nonNull(icOutstandingDTO.getBeneficiary().getMiddleName()) ? icOutstandingDTO.getBeneficiary().getMiddleName() : "";
                String beneficiaryLastName = Objects.nonNull(icOutstandingDTO.getBeneficiary().getLastName()) ? icOutstandingDTO.getBeneficiary().getLastName() : "";

                remittanceData.setCreditorName(beneficiaryFirstName + " " + beneficiaryMiddleName + " " + beneficiaryLastName);
                String beneficiaryPhoneNo = Objects.nonNull(icOutstandingDTO.getBeneficiary().getPhoneNumber()) ? icOutstandingDTO.getBeneficiary().getPhoneNumber() : icOutstandingDTO.getBeneficiary().getMobileNumber();
                remittanceData.setPhoneNo(beneficiaryPhoneNo);

                if (isNotBlankAndNotEmpty(icOutstandingDTO.getBeneficiary().getAddress())) {
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
        if (isNotBlankAndNotEmpty(icOutstandingDTO.getRemitter())) {
            remittanceData.setSenderPhone(isNotBlankAndNotEmpty(icOutstandingDTO.getRemitter().getPhoneNumber()) ? icOutstandingDTO.getRemitter().getPhoneNumber() : icOutstandingDTO.getRemitter().getMobileNumber());
            String senderFirstName = Objects.nonNull(icOutstandingDTO.getRemitter().getFirstName()) ? icOutstandingDTO.getRemitter().getFirstName() : "";
            String senderMiddleName = Objects.nonNull(icOutstandingDTO.getRemitter().getMiddleName()) ? icOutstandingDTO.getRemitter().getMiddleName() : "";
            String senderLastName = Objects.nonNull(icOutstandingDTO.getRemitter().getLastName()) ? icOutstandingDTO.getRemitter().getLastName() : "";
            remittanceData.setSenderName(senderFirstName + " " + senderMiddleName + " " + senderLastName);

            if (isNotBlankAndNotEmpty(icOutstandingDTO.getRemitter().getAddress())) {
                String address1 = Objects.nonNull(icOutstandingDTO.getRemitter().getAddress().getAddressLine1()) ? icOutstandingDTO.getRemitter().getAddress().getAddressLine1() : "";
                String address2 = Objects.nonNull(icOutstandingDTO.getRemitter().getAddress().getAddressLine2()) ? icOutstandingDTO.getRemitter().getAddress().getAddressLine2() : "";
                remittanceData.setSenderAddress(address1 + " " + address2);
            }

            if (isNotBlankAndNotEmpty(icOutstandingDTO.getRemitter().getIdDetails())) {
                remittanceData.setIdNo(icOutstandingDTO.getRemitter().getIdDetails().getNumber());
                remittanceData.setSenderIdType(icOutstandingDTO.getRemitter().getIdDetails().getType());
            }
        }
    }

    // if the first 3 digits of the bankCode is 185 (RUPALI Bank Head Office) then the transaction type is EFT.
    private void mapBankBranchInfo(RemittanceData remittanceData, ICOutstandingTransactionDTO icOutstandingDTO, Map<Integer, Branch> branchMap, Map<String, MbkBrn> mbkBrnMap) {
        try {
            boolean isBankDetailsNonEmpty = isNotBlankAndNotEmpty(icOutstandingDTO.getBeneficiary()) && isNotBlankAndNotEmpty(icOutstandingDTO.getBeneficiary().getBankDetails());
            ICBankDetailsDTO icBankDetailsDTO = icOutstandingDTO.getBeneficiary().getBankDetails();
            if (isBankDetailsNonEmpty) {
                String routingNumber = icBankDetailsDTO.getBankCode();
                remittanceData.setBranchRoutingNumber(routingNumber);

                String address1 = Objects.nonNull(icBankDetailsDTO.getBankAddress1()) ? icBankDetailsDTO.getBankAddress1() : "";
                String address2 = Objects.nonNull(icBankDetailsDTO.getBankAddress2()) ? icBankDetailsDTO.getBankAddress2() : "";
                remittanceData.setBankName(icBankDetailsDTO.getBankName());
                remittanceData.setBranchName(icBankDetailsDTO.getBankName() + ", " + address1 + ", " + address2);
                remittanceData.setCreditorAccountNo(icBankDetailsDTO.getBankAccountNumber());
                remittanceData.setBankCode(routingNumber);
                if (!remittanceData.isDuplicate()) {
                    remittanceData.setProcessStatus(RemittanceData.OPEN);

                    Predicate<String> isValid = routing -> isNotBlankAndNotEmpty(routing) && routing.length() > 2 && bankCode.equals(routing.substring(0, 3));
                    if (isValid.test(routingNumber)) {
                        remittanceData.setRemittanceMessageType(RemittanceData.EFT);
                        remittanceData.setBankCode(bankCode);
                        remittanceData.setBranchCode(Integer.parseInt(routingNumber.substring(3)));
                        if (routingNumber.length() > 3)
                            remittanceData.setOwnBranchCode(Integer.parseInt(routingNumber.substring(3)));
                    } else {
                        remittanceData.setRemittanceMessageType(RemittanceData.BEFTN);
                    }
                    RemittanceValidator.validateOutstandingRemittanceData(remittanceData, branchMap, mbkBrnMap);
                }
            }
        } catch (Exception e) {
            logger.error("Error in mapBankBranchInfo()", e);
        }
    }


}
