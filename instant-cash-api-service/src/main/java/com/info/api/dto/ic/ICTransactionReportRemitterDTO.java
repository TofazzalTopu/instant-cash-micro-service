package com.info.api.dto.ic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ICTransactionReportRemitterDTO {

    private String name;
    private String address;
    private String mobileNumber;
    private String phoneNumber;
    private String primaryIdType;
    private String primaryIdNumber;

}
