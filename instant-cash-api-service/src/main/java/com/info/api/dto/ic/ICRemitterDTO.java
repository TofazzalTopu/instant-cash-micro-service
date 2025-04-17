package com.info.api.dto.ic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ICRemitterDTO {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    private String middleName;
    private String phoneNumber;

    /**
     * Mandatory if receiving country is Bangladesh and delivery mode is Mobile
     * Money/bKash (only for Banks).
     * Mandatory if receiving country is UAE.
     */
    private String mobileNumber;

    /**
     * Mandatory if receiving country is UAE.
     * Mandatory if receiving country is Tanzania.
     * Mandatory if receiving country is Ethiopia and delivery mode is 4 (Account
     * Transfer to Other Bank) or 10 (Mobile Money Transfer).
     * Mandatory if receiving country is Uganda and delivery mode is 4 (Account
     * Transfer to Other Bank) or 10 (Mobile Money Transfer).
     */
    private Date dateOfBirth;
    @NotEmpty
    private String nationality;
    private String nationalityCountryCode;

    @NotEmpty
    private ICAddressDTO address;
    private ICIdDetailsDTO idDetails;




}
