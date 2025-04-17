package com.info.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class BaseRemittanceData implements Serializable {

    private static final long serialVersionUID = 1L;


    @Column(name = "REMITTANCE_MSG_TYPE ")
    String remittanceMessageType;

    @Column(name = "BANK_CODE")
    String bankCode;

    @Column(name = "BANK_NAME")
    String bankName;

    @Column(name = "BRANCH_ROUNTING_NO")
    String branchRoutingNumber;

    @Column(name = "BRANCH_NAME")
    String branchName;

    @Column(name = "CREDITOR_ACCOUNT_NO")
    String creditorAccountNo;

    @Column(name = "CREDITOR_NAME")
    String creditorName;

    @Column(name = "PROCESS_DATE")
    @Temporal(TemporalType.DATE)
    Date processDate;

    @Column(name = "REFERENCE_NO")
    String referenceNo;

    @Column(name = "REFERENCE_DATE")
    @Temporal(TemporalType.DATE)
    Date referenceDate;

    @Column(name = "AMOUNT")
    BigDecimal amount;

    @Column(name = "EXCHANGE_HOUSE_CODE")
    String exchangeCode;

    @Column(name = "EXCHANGE_HOUSE_NAME")
    String exchangeName;

    @Column(name = "SECURITY_CODE")
    String securityCode;

    @Column(name = "ID_NO")
    String idNo;

    @Column(name = "SENDER_ID_TYPE")
    String senderIdType;

    @Column(name = "PHONE_NO")
    String phoneNo;

    @Column(name = "COUNTRY_ORIGINATE")
    String countryOriginate;

    @Column(name = "SENDER_PHONE")
    String senderPhone;

    @Column(name = "SENDER_NAME")
    String senderName;

    @Column(name = "SENDER_ADDRESS")
    String senderAddress;

    @Column(name = "RECIEVER_CITY_DISTRICT")
    String cityDistrict;

    @Column(name = "CURRENCY_ORIGINATE")
    String currencyOriginate;

    @Column(name = "AMOUNT_ORIGINATE")
    BigDecimal amountOriginate;

    @Column(name = "EX_TRANSACTION_NO")
    String exchangeTransactionNo;

    @Column(name = "RECEIVER_ADDRESS")
    String receiverAddress;

    @Column(name = "EX_TRANSACTION_DATE")
    String exchangeTransactionDate;

    @Column(name = "PURPOSE")
    String purpose;

    @Column(name = "PAID_BRANCH")
    String paidBranch;

    @Column(name = "PAID_BY")
    String paidBy;

    @Column(name = "CHARGES")
    BigDecimal charges;

    @Column(name = "SOURCE_TYPE")
    String sourceType;

    @Column(name = "SENDER_DOC")
    String senderDoc;

    @Column(name = "API_TRY_COUNT")
    long tryCount;

    @Column(name = "API_RESPONSE")
    String apiResponse;

    @ManyToOne
    @JoinColumn(name = "REMITTANCE_PROCESS_ID")
    RemittanceProcessMaster remittanceProcessMaster;

    @Column(name = "ENTEREDBY")
    String enteredBy;

    @Column(name = "AUTHORIZE_BY_USER")
    String authorizedBy;

    @Column(name = "TRANSACTION_BATCH")
    Integer transactionBatchNo;

    @Column(name = "TRANSACTION_BRANCH")
    Integer transactionBranch;

    @Column(name = "TRANSACTION_DATE")
    @Temporal(TemporalType.DATE)
    Date transactionDate;

    @Column(name = "TRANSACTION_BY_USER")
    String transactionBy;

    @Column(name = "PROCCESS_STATUS")
    String processStatus;

    @Column(name = "REASON_FOR_INVALID")
    String reasonForInvalid;

    @Column(name = "OWN_BRANCH_CODE")
    Integer ownBranchCode;

    @Column(name = "ORG_BANK_CODE")
    String orgBankCode;

    @Column(name = "ORG_ROUNTING_NO")
    String orgRoutingNo;

    @Column(name = "ORG_SECURITY_CODE")
    String orgSecurityCode;

    @Column(name = "MODIFIED_USER")
    String modifiedUser;

    @Column(name = "EFT_AUTOCREDIT")
    String eftAutoCredit;

    @Column(name = "FINAL_STATUS")
    String finalStatus;

    @Column(name = "MIDDLE_WARE_PUSH")
    Integer middlewarePush;

    @Column(name = "MIDDLE_WARE_ID")
    Long middlewareId;

    @Column(name = "BRN_PAYMENT_DATE")
    @Temporal(TemporalType.DATE)
    Date brnPaymentDate;

    @Column(name = "EXCHANGE_RATE")
    BigDecimal exchangeRate;

    @Column(name = "REMARKS")
    String remarks;

    @Column(name = "STOP_PAYREASON")
    String stopPayReason;

    @Column(name = "CURRENCY_CODE")
    String currencyCode;

    @Column(name = "BRANCH_CODE")
    Integer branchCode;

    @Column(name = "RETURNED_MSG")
    Integer returnedMsg;

    @Column(name = "RETURNED_DATE")
    @Temporal(TemporalType.DATE)
    Date returnedDate;

    @Column(name = "RETURNED_BY")
    String returnedBy;

    @Column(name = "CURRENT_STATE")
    String currentState;

    @Column(name = "FC_AMOUNT")
    BigDecimal fcAmount;

    @Column(name = "INCENTIVE_AMOUNT")
    BigDecimal incentiveAmount;

    @Column(name = "INCENTIVE_GIVEN")
    String incentiveGiven;

    @Column(name = "SENDER_GENDER")
    String senderGender;

    @Column(name = "RECEIVER_GENDER")
    String receiverGender;

    @Column(name = "SENDER_OCCUPATION")
    String senderOccupation;

    @Column(name = "RECEIVER_OCCUPATION")
    String receiverOccupation;

    @Transient
    private boolean selected;


    @Transient
    private boolean duplicate = false;

    @Transient
    private boolean eligibleForReturnOrCredit;
}
