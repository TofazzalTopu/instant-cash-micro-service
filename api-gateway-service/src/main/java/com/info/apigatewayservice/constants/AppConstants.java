package com.info.apigatewayservice.constants;

public class AppConstants {


    private AppConstants() {
    }

    public static final String API_VERSION_V1 = "v1";
    public static final String API_VERSION_V2 = "v2";
    public static final String API = "/api/";
    public static final String INSTANT_CASH = "/instant-cash";
    public static final String PRODUCTS = API + "/products";
    public static final String API_ENDPOINT = API + API_VERSION_V1;
    public static final String PRODUCTS_API_ENDPOINT = API_ENDPOINT + "/products";
    public static final String USERS = "/users";
    public static final String BANK = "/bank";
    public static final String BRANCH = "/branch";
    public static final String MBK_BRN = "/mbk-brn";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized Access";
    public static final String API_STATUS_VALID = "VALID";
    public static final String API_STATUS_INVALID = "INVALID";
    public static final String API_STATUS_ERROR = "ERROR";
    public static final String API_STATUS_ERROR_TIMEOUT = "ERROR_TIMEOUT";

}
