package com.info.bank.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class BranchKey implements Serializable {
	@Column(name = "MBRN_ENTITY_NUM", nullable = false)
	private short entityNumber;
	
	@Column(name = "MBRN_CODE", nullable = false)
	private int branchCode;

	public short getEntityNumber() {
		return entityNumber;
	}

	public void setEntityNumber(short entityNumber) {
		this.entityNumber = entityNumber;
	}

	public int getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(int branchCode) {
		this.branchCode = branchCode;
	}

}
