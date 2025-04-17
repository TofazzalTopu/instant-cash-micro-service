package com.info.api.dto.ic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ICBeneficiaryDTO {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    private String middleName;
    private String phoneNumber;
    private String mobileNumber;
    @NotEmpty
    private ICAddressDTO address;
    private ICIdDetailsDTO idDetails;
    private Date dateOfBirth;

    @NotEmpty
    private String nationality;
    private String expectedId;
    private String relationship;
    private String expectedBenefId;

    private ICBankDetailsDTO bankDetails;
}
