package com.info.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class PaymentApiResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7969298090989117693L;

	@JsonProperty("PayoutStatus")
	private String payoutStatus;

	@JsonProperty("ApiStatus")
	private String apiStatus;

	@JsonProperty("ErrorMessage")
	private String errorMessage;

	@JsonProperty("TransRefID")
	private String transRefID;

	@JsonProperty("TranNo")
	private String tranNo;

	@JsonProperty("OriginalRequest")
	private String originalRequest;

	@JsonProperty("OriginalResponse")
	private String originalResponse;

	public String getPayoutStatus() {
		return payoutStatus;
	}

	public void setPayoutStatus(String payoutStatus) {
		this.payoutStatus = payoutStatus;
	}

	public String getApiStatus() {
		return apiStatus;
	}

	public void setApiStatus(String apiStatus) {
		this.apiStatus = apiStatus;
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

	public String getTransRefID() {
		return transRefID;
	}

	public void setTransRefID(String transRefID) {
		this.transRefID = transRefID;
	}

	public String getTranNo() {
		return tranNo;
	}

	public void setTranNo(String tranNo) {
		this.tranNo = tranNo;
	}

	public String getOriginalRequest() {
		return originalRequest;
	}

	public void setOriginalRequest(String originalRequest) {
		this.originalRequest = originalRequest;
	}

}
