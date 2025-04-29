package com.info.accounts.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "ACCOUNTS")
public class AccountBalance implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected AccountKey accountKey;

    @Column(name = "ACCOUNT_NAME")
    private String accountName;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "CONTRACT_NUM")
    private String contractNumber;

    @Column(name = "BRANCH_CODE")
    private Integer branchCode;

    @Column(name = "ACCOUNT_BALANCE")
    private Double accountBalance;

    @Column(name = "AVAILABLE_BALANCE")
    private Double availableBalance;

    @Column(name = "ACNTCBAL_AC_AMT_ON_HOLD")
    private Double amountOnHold;

    @Column(name = "CLIENT_NUMBER")
    private Long clientNumber;

    @Column(name = "PRODUCT_CODE")
    private Short productCode;

    @Column(name = "GL_ACCESS_CODE")
    private String glAccessCode;

    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    @Column(name = "INOPERATIVE")
    private Character inoperative;

    @Column(name = "DORMANT")
    private boolean dormant;

    @Column(name = "ABB_TRAN_ALLOWED")
    private Character onlineTransactionAllowed;

    @Column(name = "DEBIT_FREEZED")
    private Character debitFreezed;

    @Column(name = "CREDIT_FREEZED")
    private Character creditFrezed;

    @Column(name = "CLOSURE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closureDate;

    @Column(name = "DECEASED_APPL")
    private Character accountDeceasedApplicable;

    @Column(name = "ACCOUNT_TYPE")
    private String accountType;

    @Column(name = "ACCOUNT_SUB_TYPE")
    private Short accountSubType;

    @Transient
    private Long corpclClientCode;

    @Transient
    boolean loanAccount = false;

}
