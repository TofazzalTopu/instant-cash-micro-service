package com.info.dto.constants;

public class RemittanceDataStatus {

    private RemittanceDataStatus() {
    }

    public static final String INVALID = "INVALID";
    public static final String VALID = "VALID";
    public static final String POSTED = "POSTED";
    public static final String COMPLETED = "COMPLETED";
    public static final String OPEN = "OPEN";
    public static final String UNLOCK = "UNLOCK";
    public static final String UNLOCK_REQUESTED = "UNLOCK_REQUESTED";
    public static final String UNLOCK_NOTIFIED = "UNLOCK_NOTIFIED";

    public static final String BEFTN = "BEFTN";
    public static final String EFT = "EFT";

    public static final String PAID = "PAID";
    public static final String RECEIVED = "RECEIVED";
    public static final String REJECTED = "REJECTED";

    public static final String NEW_STATUS_D = "D";
    public static final String NEW_STATUS_X = "X";
    public static final String NEW_STATUS_Y = "Y";
}
