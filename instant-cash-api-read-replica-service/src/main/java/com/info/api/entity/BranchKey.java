package com.info.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BranchKey implements Serializable {
	@Column(name = "MBRN_ENTITY_NUM", nullable = false)
	private short entityNumber;
	
	@Column(name = "MBRN_CODE", nullable = false)
	private int branchCode;


}
