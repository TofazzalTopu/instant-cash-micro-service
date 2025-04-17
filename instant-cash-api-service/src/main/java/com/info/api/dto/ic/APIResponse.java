package com.info.api.dto.ic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.info.api.constants.Constants;
import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse<T> {

    @JsonProperty("ErrorMessage")
    private String errorMessage;

    @JsonProperty("ApiStatus")
    private String apiStatus = Constants.API_STATUS_VALID;

    private T data;

}
