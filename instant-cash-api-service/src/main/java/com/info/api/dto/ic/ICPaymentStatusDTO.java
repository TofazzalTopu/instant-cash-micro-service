package com.info.api.dto.ic;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ICPaymentStatusDTO implements Serializable {

    private static final long serialVersionUID = -7969298090989117693L;

    @NotBlank
    private String reference;

    @NotBlank
    private String status;

    @NotBlank
    private String statusDescription;

    @NotBlank
    private String payingAgentCorrespondentId;

    @NotBlank
    private String payingAgentName;

    @NotBlank
    private String sentAt;

}
