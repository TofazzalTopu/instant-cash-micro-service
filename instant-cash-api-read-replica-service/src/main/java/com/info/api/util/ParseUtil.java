package com.info.api.util;

import com.info.dto.constants.Constants;
import com.info.dto.response.APIResponse;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Objects;

public class ParseUtil {

    private ParseUtil() {
    }

    public static boolean isNullOrEmpty(String value) {
        return Objects.isNull(value) || value.isEmpty();
    }

    public static boolean isNullOrEmpty(String... values) {
        return Arrays.stream(values).anyMatch(v -> v == null || v.isEmpty());
    }

    public static APIResponse<String> unAuthorized() {
        APIResponse<String> apiResponse = new APIResponse<>();
        apiResponse.setApiStatus(HttpStatus.UNAUTHORIZED.name());
        apiResponse.setMessage(Constants.UNAUTHORIZED_ACCESS);
        apiResponse.setData(Constants.UNAUTHORIZED_ACCESS);
        return apiResponse;
    }

}
