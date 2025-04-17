package com.info.api.mapper;

import com.info.api.dto.PaymentApiRequest;
import com.info.api.dto.ic.ICConfirmBeneficiaryDTO;
import com.info.api.entity.RemittanceData;
import com.info.api.dto.ic.ICAddressDTO;
import com.info.api.dto.ic.ICConfirmDTO;
import com.info.api.dto.ic.ICIdDetailsDTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ICConfirmDTOMapper {

    private ICConfirmDTOMapper() {
    }

    public static ICConfirmDTO mapICConfirmDTO(RemittanceData remittanceData, String newStatus, String remarks) {
        return ICConfirmDTO.builder().reference(remittanceData.getReferenceNo()).newStatus(newStatus).remarks(remarks).beneficiaryDetails(mapICConfirmBeneficiaryDTO(remittanceData)).build();
    }

    public static ICConfirmBeneficiaryDTO mapICConfirmBeneficiaryDTO(RemittanceData remittanceData) {
        ICConfirmBeneficiaryDTO icConfirmBeneficiaryDTO = new ICConfirmBeneficiaryDTO();
        icConfirmBeneficiaryDTO.setMobileNumber(remittanceData.getPhoneNo());
        icConfirmBeneficiaryDTO.setAddress(mapICAddressDTO(remittanceData));
        icConfirmBeneficiaryDTO.setPrimaryId(mapICIdDetailsDTO(remittanceData));
        return icConfirmBeneficiaryDTO;
    }

    public static ICAddressDTO mapICAddressDTO(RemittanceData remittanceData) {
        return ICAddressDTO.builder().addressLine1(remittanceData.getReceiverAddress()).city(remittanceData.getCityDistrict()).district(remittanceData.getCityDistrict()).state(remittanceData.getCurrentState()).country(remittanceData.getCityDistrict()).build();
    }

    public static ICIdDetailsDTO mapICIdDetailsDTO(RemittanceData remittanceData) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String processDate = df.format(remittanceData.getProcessDate());
        return ICIdDetailsDTO.builder().type(remittanceData.getIdNo()).number(remittanceData.getIdNo()).issueDate(processDate).placeOfIssue(remittanceData.getCityDistrict()).build();
    }

    public static ICConfirmDTO mapICConfirmDTO(PaymentApiRequest paymentApiRequest, String newStatus, String remarks) {
        return ICConfirmDTO.builder().reference(paymentApiRequest.getPinno()).newStatus(newStatus).remarks(remarks).beneficiaryDetails(mapICConfirmBeneficiaryDTO(paymentApiRequest)).build();
    }

    public static ICConfirmBeneficiaryDTO mapICConfirmBeneficiaryDTO(PaymentApiRequest paymentApiRequest) {
        ICConfirmBeneficiaryDTO icConfirmBeneficiaryDTO = new ICConfirmBeneficiaryDTO();
        icConfirmBeneficiaryDTO.setCountryOfBirth(paymentApiRequest.getBeneIDIssuedByCountry());
        icConfirmBeneficiaryDTO.setCountryOfResidence(paymentApiRequest.getBeneIDIssuedByCountry());
        icConfirmBeneficiaryDTO.setNationality(paymentApiRequest.getBeneIDIssuedByCountry());
        icConfirmBeneficiaryDTO.setMobileNumber(paymentApiRequest.getMobileNo());
        icConfirmBeneficiaryDTO.setRelation(paymentApiRequest.getBeneCustRelationship());
        icConfirmBeneficiaryDTO.setAddress(mapICAddressDTO(paymentApiRequest));
        icConfirmBeneficiaryDTO.setPrimaryId(mapICIdDetailsDTO(paymentApiRequest));
        return icConfirmBeneficiaryDTO;
    }

    public static ICAddressDTO mapICAddressDTO(PaymentApiRequest paymentApiRequest) {
        return ICAddressDTO.builder().addressLine1(paymentApiRequest.getAddress()).city(paymentApiRequest.getCity()).district(paymentApiRequest.getCity()).state(paymentApiRequest.getBeneIDIssuedByState()).country(paymentApiRequest.getBeneIDIssuedByCountry()).build();
    }

    public static ICIdDetailsDTO mapICIdDetailsDTO(PaymentApiRequest paymentApiRequest) {
        return ICIdDetailsDTO.builder().type(paymentApiRequest.getBeneIDType()).number(paymentApiRequest.getBeneIDNumber()).issueDate(paymentApiRequest.getBeneIDIssueDate()).expiryDate(paymentApiRequest.getBeneIDExpirationDate()).placeOfIssue(paymentApiRequest.getBeneIDIssuedByState()).build();
    }

}
