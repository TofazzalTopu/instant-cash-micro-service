package com.info.bank.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class MbkBrnKey implements Serializable {

	private static final long serialVersionUID = 6283226778155942356L;

	@Column(name = "MBKBRN_BANK_CODE")
	private String bankCode;

	@Column(name = "MBKBRN_BRN_CODE")
	private String branchRouting;


}
