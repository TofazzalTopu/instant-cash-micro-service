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
public class ICOutstandingTransactionDTO {


    @NotEmpty
    private String reference;
    private String partnerReference;
    @NotEmpty
    private String payingAgentCorrespondentId;
    @NotEmpty
    private String payingAgentName;
    private String payingAgentCommShare;

    @NotEmpty
    private BigDecimal payingAmount;
    @NotEmpty
    private String payingCurrency;
    private String settlementCurrency;

    @NotEmpty
    private BigDecimal commissionAmount;

    @NotEmpty
    private BigDecimal settlementAmount;

    @NotEmpty
    private BigDecimal settlementRate;
    @NotEmpty
    private String remittancePurpose;
    @NotEmpty
    private String deliveryMode;
    @NotEmpty
    private String originatingCountry;
    @NotEmpty
    private String destinationCountry;

    private String messagePayeeBranch;
    @NotEmpty
    private String sourceOfFunds;
    /**
     * Format: yyyy-MM-dd (ISO-8601)
     */
    @NotEmpty
    private String sentAt;
    @NotEmpty
    private ICRemitterDTO remitter;
    @NotEmpty
    private ICBeneficiaryDTO beneficiary;
    private boolean duplicate = false;

    private String reasonForInvalid;
    private String processStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ICOutstandingTransactionDTO that = (ICOutstandingTransactionDTO) o;
        return Objects.equals(reference, that.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference);
    }

}
