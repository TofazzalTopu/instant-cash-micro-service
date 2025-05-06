package com.info.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "REM_API_TRACE")
public class ApiTrace implements Serializable {

    private static final long serialVersionUID = 22430169541112512L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REM_API_TRACE_SEQ_GEN")
    @SequenceGenerator(name = "REM_API_TRACE_SEQ_GEN", sequenceName = "REM_API_TRACE_SEQ", allocationSize = 1)
    private long id;

    @Column(name = "EXCHANGE_CODE")
    private String exchangeCode;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "BRANCH_CODE")
    private Integer branchCode;

    @Column(name = "PIN_NO")
    private String pinNo;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    @Column(name = "REQ_TYPE")
    private String requestType;

    @Column(name = "RESPONSE_MSG")
    private String responseMsg;

    @Column(name = "STATUS")
    private String status;

    @Temporal(TemporalType.DATE)
    @Column(name = "CBS_DATE")
    private Date cbsDate;

    @Column(name = "TRAN_NO")
    private String tranno;

    @Column(name = "EX_TRAN_ID")
    private String exTRANID;

    @Column(name = "REQUEST_MSG")
    private String requestMsg;

    @Column(name = "CANCEL_STATUS")
    private String cancelStatus;

    @Column(name = "CBS_BATCH_INFO")
    private String cbsBatchInfo;

    @Column(name = "CBS_SETTLEMENT_STATUS")
    private String cbsSettlementStatus;

    @Column(name = "TRY_COUNT")
    private int tryCount;


}
