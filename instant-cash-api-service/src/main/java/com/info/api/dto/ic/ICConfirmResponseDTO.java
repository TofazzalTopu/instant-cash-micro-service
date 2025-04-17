package com.info.api.dto.ic;


import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class ICConfirmResponseDTO {

    @NotBlank
    private String reference;

    @NotBlank
    private String status;

    @NotBlank
    private String statusDescription;

}
