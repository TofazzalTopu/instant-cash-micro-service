package com.info.dto.account;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PaymentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String accountNumber;
    @NotNull
    private Integer branchCode;
    @NotNull
    private Double amount;
    @NotNull
    private Short productCode;
    @NotNull
    private String currencyCode;
    @NotNull
    private String reference;

}
