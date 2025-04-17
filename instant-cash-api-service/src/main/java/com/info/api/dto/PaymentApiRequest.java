package com.info.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentApiRequest implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6326694665210595043L;

	@JsonProperty("ExchCode")
	private String exchCode;

	@JsonProperty("Pinno")
	private String pinno;

	@JsonProperty("BrUserId")
	private String brUserId;

	@JsonProperty("BrCode")
	private String brCode;

	@JsonProperty("NID")
	private String beneIDNumber;

	@JsonProperty("DOB")
	private String dob;

	@JsonProperty("TranNo")
	private String tranNo;

	@JsonProperty("Address")
	private String address;

	@JsonProperty("City")
	private String city;

	@JsonProperty("ZipCode")
	private String zipCode;

	@JsonProperty("MobileNo")
	private String mobileNo;

	@JsonProperty("PurposeOfTran")
	private String purposeOfTran;

	@JsonProperty("RelationWithRemitter")
	private String relationWithRemitter;

	@JsonProperty("ipAddress")
	private String ipAddress;

	@JsonProperty("BeneIDType")
	private String beneIDType;

	@JsonProperty("BeneIDIssuedBy")
	private String beneIDIssuedBy;

	@JsonProperty("BeneIDIssuedByCountry")
	private String beneIDIssuedByCountry;

	@JsonProperty("BeneIDIssuedByState")
	private String beneIDIssuedByState;

	@JsonProperty("BeneIDIssueDate")
	private String beneIDIssueDate;

	@JsonProperty("BeneIDExpirationDate")
	private String beneIDExpirationDate;

	@JsonProperty("BeneOccupation")
	private String beneOccupation;

	@JsonProperty("BeneGender")
	private String beneGender;

	@JsonProperty("BeneTaxID")
	private String beneTaxID;

	@JsonProperty("BeneCustRelationship")
	private String beneCustRelationship;


}
