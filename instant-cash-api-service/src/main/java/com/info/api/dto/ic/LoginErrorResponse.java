package com.info.api.dto.ic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginErrorResponse {

    private static final long serialVersionUID = -7969298090989117693L;

    @JsonProperty("PayoutStatus")
    private String payoutStatus;

    @JsonProperty("ApiStatus")
    private String apiStatus;

    @JsonProperty("ErrorMessage")
    private String errorMessage;

    @JsonProperty("TransRefID")
    private String transRefID;

    @JsonProperty("TranNo")
    private String tranNo;

    @JsonProperty("OriginalRequest")
    private String originalRequest;

    @JsonProperty("OriginalResponse")
    private String originalResponse;


}
