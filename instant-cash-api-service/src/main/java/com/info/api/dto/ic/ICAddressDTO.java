package com.info.api.dto.ic;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ICAddressDTO {

    @NotEmpty
    private String addressLine1;
    private String addressLine2;
    //Mandatory if receiving country is Bangladesh.
    private String district;
    @NotEmpty
    private String city;
    private String postCode;
    private String state;

    //Mandatory if receiving country is UAE.
    private String country;
}
