package com.info.dto.account;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AccountDTO {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String accountName;
    @NotNull
    private String accountNumber;
    @NotNull
    private String internalAccountNumber;
    @NotNull
    private Integer branchCode;
    @NotNull
    private Short productCode;
    @NotNull
    private String glAccessCode;
    @NotNull
    private String currencyCode;
    private Character inoperative;
    private boolean dormant;
    private Character onlineTransactionAllowed;
    private Character debitFreezed;
    private Character creditFreezed;
    private Date closureDate;
    private Character accountDeceasedApplicable;
    private String accountType;
    private boolean accountSubType;
    private Long corpClientCode;

    boolean loanAccount = false;


}
