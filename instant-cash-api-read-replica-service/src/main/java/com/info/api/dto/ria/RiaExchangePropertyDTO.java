package com.info.api.dto.ria;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiaExchangePropertyDTO {

    @NotBlank
    private String exchangeCode;
    @NotBlank
    private String agentId;
    @NotBlank
    private String ocpApimSubKey;
    @NotBlank
    private String apiVersion;
    @NotBlank
    private String downloadableUrl;
    @NotBlank
    private String searchUrl;
    @NotBlank
    private String paymentUrl;
    @NotBlank
    private String cashPickUpCancelUrl;
    @NotBlank
    private String notifyRemStatusUrl;
    @NotBlank
    private String notifyCancelReqUrl;

}
