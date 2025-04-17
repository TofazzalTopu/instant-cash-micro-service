package com.info.api.dto.ic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ICTransactionReportDTO {

    @NotEmpty
    private String reference;
    private String partnerReference;
    private BigDecimal customerPrincAmount;
    private BigDecimal foreignCurrencyAmount;
    @NotEmpty
    private String deliveryMode;
    @NotEmpty
    private String payingCurrency;
    @NotEmpty
    private BigDecimal settlementAmount;
    @NotEmpty
    private BigDecimal settlementRate;
    @NotEmpty
    private String receivingAgentCode;
    @NotEmpty
    private String receivingAgentName;

    @NotEmpty
    private String transactionType;

    @NotEmpty
    private String transactionStatus;

    @NotEmpty
    private String transactionStatusDescription;

    @NotEmpty
    private String originatingCountry;

    @NotEmpty
    private String remittancePurpose;
    private String messagePayeeBranch;
    private BigDecimal payingAgentCommisionShareAmount;
    private BigDecimal percentageRefundCommision;
    private BigDecimal totalCommission;
    private String transactionDate;
    private String transactionCancelDate;
    private BigDecimal agentExchangeEarning;
    private BigDecimal icExchangeEarning;


    /**
     * Format: yyyy-MM-dd (ISO-8601)
     */
    @NotEmpty
    private ICTransactionReportAgentDTO agent;
    @NotEmpty
    private ICTransactionReportRemitterDTO remitter;
    @NotEmpty
    private ICTransactionReportBeneficiaryDTO beneficiary;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ICTransactionReportDTO that = (ICTransactionReportDTO) o;
        return Objects.equals(reference, that.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference);
    }

}
