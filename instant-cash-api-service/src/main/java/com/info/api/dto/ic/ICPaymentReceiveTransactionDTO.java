package com.info.api.dto.ic;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Objects;

@Data
public class ICPaymentReceiveTransactionDTO {

    @NotEmpty
    private String reference;
    private String partnerReference;
    @NotEmpty
    private String payingAgentCorrespondentId;
    @NotEmpty
    private String payingAgentName;
    @NotEmpty
    private BigDecimal payingAmount;
    @NotEmpty
    private String payingCurrency;
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
     Format: yyyy-MM-dd (ISO-8601)
     */
    @NotEmpty
    private String sentAt;
    @NotEmpty
    private ICRemitterDTO remitter;
    @NotEmpty
    private ICBeneficiaryDTO beneficiary;


    public ICPaymentReceiveTransactionDTO() {
    }

    public ICPaymentReceiveTransactionDTO(String reference, String partnerReference, String payingAgentCorrespondentId, String payingAgentName, BigDecimal payingAmount, String payingCurrency, BigDecimal commissionAmount, BigDecimal settlementAmount, BigDecimal settlementRate, String remittancePurpose, String deliveryMode, String originatingCountry, String destinationCountry, String messagePayeeBranch, String sourceOfFunds, String sentAt, ICRemitterDTO remitter, ICBeneficiaryDTO beneficiary) {
        this.reference = reference;
        this.partnerReference = partnerReference;
        this.payingAgentCorrespondentId = payingAgentCorrespondentId;
        this.payingAgentName = payingAgentName;
        this.payingAmount = payingAmount;
        this.payingCurrency = payingCurrency;
        this.commissionAmount = commissionAmount;
        this.settlementAmount = settlementAmount;
        this.settlementRate = settlementRate;
        this.remittancePurpose = remittancePurpose;
        this.deliveryMode = deliveryMode;
        this.originatingCountry = originatingCountry;
        this.destinationCountry = destinationCountry;
        this.messagePayeeBranch = messagePayeeBranch;
        this.sourceOfFunds = sourceOfFunds;
        this.sentAt = sentAt;
        this.remitter = remitter;
        this.beneficiary = beneficiary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ICPaymentReceiveTransactionDTO that = (ICPaymentReceiveTransactionDTO) o;
        return Objects.equals(reference, that.reference) && Objects.equals(partnerReference, that.partnerReference) && Objects.equals(payingAmount, that.payingAmount) && Objects.equals(originatingCountry, that.originatingCountry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference, partnerReference, payingAmount, originatingCountry);
    }

}
