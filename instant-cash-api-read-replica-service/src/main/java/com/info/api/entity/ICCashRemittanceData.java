package com.info.api.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "IC_CASH_REMITTANCE_DATA")
public class ICCashRemittanceData extends BaseRemittanceData {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CASH_REMITTANCE_DATA_GEN")
	@SequenceGenerator(name = "CASH_REMITTANCE_DATA_GEN", sequenceName = "CASH_REMITTANCE_DATA_SEQ", allocationSize = 1)
	private Long id;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ICCashRemittanceData that = (ICCashRemittanceData) o;
		return Objects.equals(bankCode, that.bankCode) && Objects.equals(processDate, that.processDate) && Objects.equals(referenceNo, that.referenceNo) && Objects.equals(amount, that.amount) && Objects.equals(exchangeCode, that.exchangeCode);
	}

	public ICCashRemittanceData(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bankCode, processDate, referenceNo, amount, exchangeCode);
	}


}