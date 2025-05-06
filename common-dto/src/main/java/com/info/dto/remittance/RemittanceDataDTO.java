package com.info.dto.remittance;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class RemittanceDataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1512225109739128652L;

	private String remittanceMessageType;

	private String bankCode;

	private String bankName;

	private String branchRoutingNumber;

	private String branchName;

	private String creditorAccountNo;

	private String creditorName;

	private Date processDate;

	private String referenceNo;

	private Date referenceDate;

	private BigDecimal amount;

	private String exchangeCode;

	private String exchangeName;

	private String securityCode;

	private String idNo;

	private String phoneNo;

	private String countryOriginate;

	private String senderPhone;

	private String senderName;

	private String senderAddress;

	private String cityDistrict;

	private String currencyOriginate;

	private BigDecimal amountOriginate;

	private String exchangeTransactionNo;

	private String receiverAddress;

	private String exchangeTransactionDate;

	private String purpose;

	private String senderDoc;

	private String orgSecurityCode;

	private BigDecimal exchangeRate;

	private String remarks;

	private String currencyCode;

	private BigDecimal fcAmount;

	public String getRemittanceMessageType() {
		return remittanceMessageType;
	}

	public void setRemittanceMessageType(String remittanceMessageType) {
		this.remittanceMessageType = remittanceMessageType;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchRoutingNumber() {
		return branchRoutingNumber;
	}

	public void setBranchRoutingNumber(String branchRoutingNumber) {
		this.branchRoutingNumber = branchRoutingNumber;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCreditorAccountNo() {
		return creditorAccountNo;
	}

	public void setCreditorAccountNo(String creditorAccountNo) {
		this.creditorAccountNo = creditorAccountNo;
	}

	public String getCreditorName() {
		return creditorName;
	}

	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public Date getReferenceDate() {
		return referenceDate;
	}

	public void setReferenceDate(Date referenceDate) {
		this.referenceDate = referenceDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getCountryOriginate() {
		return countryOriginate;
	}

	public void setCountryOriginate(String countryOriginate) {
		this.countryOriginate = countryOriginate;
	}

	public String getSenderPhone() {
		return senderPhone;
	}

	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getCityDistrict() {
		return cityDistrict;
	}

	public void setCityDistrict(String cityDistrict) {
		this.cityDistrict = cityDistrict;
	}

	public String getCurrencyOriginate() {
		return currencyOriginate;
	}

	public void setCurrencyOriginate(String currencyOriginate) {
		this.currencyOriginate = currencyOriginate;
	}

	public BigDecimal getAmountOriginate() {
		return amountOriginate;
	}

	public void setAmountOriginate(BigDecimal amountOriginate) {
		this.amountOriginate = amountOriginate;
	}

	public String getExchangeTransactionNo() {
		return exchangeTransactionNo;
	}

	public void setExchangeTransactionNo(String exchangeTransactionNo) {
		this.exchangeTransactionNo = exchangeTransactionNo;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getExchangeTransactionDate() {
		return exchangeTransactionDate;
	}

	public void setExchangeTransactionDate(String exchangeTransactionDate) {
		this.exchangeTransactionDate = exchangeTransactionDate;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getSenderDoc() {
		return senderDoc;
	}

	public void setSenderDoc(String senderDoc) {
		this.senderDoc = senderDoc;
	}

	public String getOrgSecurityCode() {
		return orgSecurityCode;
	}

	public void setOrgSecurityCode(String orgSecurityCode) {
		this.orgSecurityCode = orgSecurityCode;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getFcAmount() {
		return fcAmount;
	}

	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}

}