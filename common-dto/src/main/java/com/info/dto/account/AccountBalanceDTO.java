package com.info.dto.account;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
public class AccountBalanceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    @NotNull
    private String accountNumber;
    @NotNull
    private Integer branchCode;
    @NotNull
    private Double accountBalance;
    @NotNull
    private Double availableBalance;
    @NotNull
    private Double amountOnHold;
    private Long clientNumber;
    @NotNull
    private Short productCode;
    private String currencyCode;

}
