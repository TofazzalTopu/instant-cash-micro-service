package com.info.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SingleAPIResponse<T> {

    @JsonProperty("ErrorMessage")
    private String errorMessage;

    @JsonProperty("ApiStatus")
    private String apiStatus;

    private T data;

}
