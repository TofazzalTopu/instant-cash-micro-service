package com.info.api.dto.ic;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ICConfirmDTO {

    @NotBlank
    private String reference;

    @NotBlank
    private String newStatus;

    private String remarks;

    private ICConfirmBeneficiaryDTO beneficiaryDetails;
}
