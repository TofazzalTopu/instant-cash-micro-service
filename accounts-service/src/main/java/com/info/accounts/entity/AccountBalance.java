package com.info.accounts.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACCOUNTS")
public class AccountBalance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "ACNTS_ENTITY_NUM")
    private int entityNumber;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ACNTS_INTERNAL_ACNUM", nullable = false)
    private long internalAccountNumber;

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
