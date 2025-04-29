package com.info.accounts.entity;

import com.sun.istack.NotNull;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AccountKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@NotNull
	@Column(name = "ACNTS_ENTITY_NUM", nullable = false)
	private short entityNumber;

	@Basic(optional = false)
	@NotNull
	@Column(name = "ACNTS_INTERNAL_ACNUM", nullable = false)
	private long internalAccountNumber;

	public short getEntityNumber() {
		return entityNumber;
	}

	public void setEntityNumber(short entityNumber) {
		this.entityNumber = entityNumber;
	}

	public long getInternalAccountNumber() {
		return internalAccountNumber;
	}

	public void setInternalAccountNumber(long internalAccountNumber) {
		this.internalAccountNumber = internalAccountNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + entityNumber;
		result = prime
				* result
				+ (int) (internalAccountNumber ^ (internalAccountNumber >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountKey other = (AccountKey) obj;
		if (entityNumber != other.entityNumber)
			return false;
		if (internalAccountNumber != other.internalAccountNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AccountKey [entityNumber=" + entityNumber
				+ ", internalAccountNumber=" + internalAccountNumber + "]";
	}

}
