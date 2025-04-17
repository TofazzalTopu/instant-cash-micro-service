package com.info.api.dto.ic;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ICExchangePropertyDTO {

    private String exchangeCode;
    private String agentId;
    private String paymentAgentId;
    private String password;
    private String ocpApimSubKey;
    private String outstandingUrl;
    private String notifyRemStatusUrl;
    private String unlockUrl;
    private String paymentReceiveUrl;
    private String statusUrl;
    private String transactionReportUrl;
    private String icPaymentUserId;

}
