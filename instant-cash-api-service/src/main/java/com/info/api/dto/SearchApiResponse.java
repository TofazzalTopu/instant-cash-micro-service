package com.info.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SearchApiResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3943089643840408168L;

	@JsonProperty("PayoutStatus")
	private String payoutStatus;

	@JsonProperty("ExchTranId")
	private String exchTranId;

	@JsonProperty("ExchCode")
	private String exchCode;
	
	@JsonProperty("OrignCountryName")
	private String orignCountryName;

	@JsonProperty("Pinno")
	private String pinno;

	@JsonProperty("Reference")
	private String reference;

	@JsonProperty("TranDate")
	private String tranDate;

	@JsonProperty("FxAmount")
	private String fxAmount;

	@JsonProperty("BenfFirstName")
	private String benfFirstName;

	@JsonProperty("RemitterName")
	private String remitterName;

	@JsonProperty("OriginalCurrency")
	private String originalCurrency;

	@JsonProperty("OriginalAmount")
	private String originalAmount;

	@JsonProperty("PayoutStatusDetails")
	private String payoutStatusDetails;

	@JsonProperty("TranNo")
	private String tranNo;

	@JsonProperty("ErrorMessage")
	private String errorMessage;

	@JsonProperty("OriginalRequest")
	private String originalRequest;
	
	@JsonProperty("OriginalResponse")
	private String originalResponse;

	@JsonProperty("ApiStatus")
	private String apiStatus;

	public String getPayoutStatus() {
		return payoutStatus;
	}

	public void setPayoutStatus(String payoutStatus) {
		this.payoutStatus = payoutStatus;
	}

	public String getExchTranId() {
		return exchTranId;
	}

	public void setExchTranId(String exchTranId) {
		this.exchTranId = exchTranId;
	}

	public String getExchCode() {
		return exchCode;
	}

	public void setExchCode(String exchCode) {
		this.exchCode = exchCode;
	}

	public String getOrignCountryName() {
		return orignCountryName;
	}

	public void setOrignCountryName(String orignCountryName) {
		this.orignCountryName = orignCountryName;
	}

	public String getPinno() {
		return pinno;
	}

	public void setPinno(String pinno) {
		this.pinno = pinno;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getFxAmount() {
		return fxAmount;
	}

	public void setFxAmount(String fxAmount) {
		this.fxAmount = fxAmount;
	}

	public String getBenfFirstName() {
		return benfFirstName;
	}

	public void setBenfFirstName(String benfFirstName) {
		this.benfFirstName = benfFirstName;
	}

	public String getRemitterName() {
		return remitterName;
	}

	public void setRemitterName(String remitterName) {
		this.remitterName = remitterName;
	}

	public String getOriginalCurrency() {
		return originalCurrency;
	}

	public void setOriginalCurrency(String originalCurrency) {
		this.originalCurrency = originalCurrency;
	}

	public String getOriginalAmount() {
		return originalAmount;
	}

	public void setOriginalAmount(String originalAmount) {
		this.originalAmount = originalAmount;
	}

	public String getPayoutStatusDetails() {
		return payoutStatusDetails;
	}

	public void setPayoutStatusDetails(String payoutStatusDetails) {
		this.payoutStatusDetails = payoutStatusDetails;
	}

	public String getTranNo() {
		return tranNo;
	}

	public void setTranNo(String tranNo) {
		this.tranNo = tranNo;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getOriginalResponse() {
		return originalResponse;
	}

	public void setOriginalResponse(String originalResponse) {
		this.originalResponse = originalResponse;
	}

	public String getApiStatus() {
		return apiStatus;
	}

	public void setApiStatus(String apiStatus) {
		this.apiStatus = apiStatus;
	}

	public String getOriginalRequest() {
		return originalRequest;
	}

	public void setOriginalRequest(String originalRequest) {
		this.originalRequest = originalRequest;
	}

}
