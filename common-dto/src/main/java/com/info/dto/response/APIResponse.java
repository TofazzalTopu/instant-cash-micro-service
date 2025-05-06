package com.info.dto.response;

import com.info.dto.constants.Constants;
import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse<T> {

    private String message;

    private String apiStatus = Constants.API_STATUS_VALID;

    private T data;

}
