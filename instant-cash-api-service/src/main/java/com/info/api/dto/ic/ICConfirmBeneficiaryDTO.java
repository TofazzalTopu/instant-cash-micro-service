package com.info.api.dto.ic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ICConfirmBeneficiaryDTO {

    //Format: yyyy-MM-dd (ISO-8601)
    private Date dateOfBirth;
    @NotEmpty
    private String countryOfBirth = "BD";
    private String countryOfResidence = "BD";
    private String mobileNumber;
    private String nationality = "BD";
    private String relation;
    private String otherRelation;
    private String pinCode;

    @NotEmpty
    private ICAddressDTO address;
    private ICIdDetailsDTO primaryId;

}
