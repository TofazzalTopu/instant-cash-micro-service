package com.info.api.dto.ic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ICTransactionReportBeneficiaryDTO {

    private String name;
    private String address;
    private String country;
    private String mobileNumber;
    private String phoneNumber;
    private String bankAccountNumber;
    private String bankAddress;
    private String bankName;

}
