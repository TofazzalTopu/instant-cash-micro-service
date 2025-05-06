package com.info.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MBRN")
public class Branch implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected BranchKey branchKey;
	@Column(name = "MBRN_NAME", length = 50)
	private String name;

	@Column(name = "MBRN_MICR_CODE")
	private Integer routingNumber;

	@Column(name = "MBRN_NUM_OFFICERS_AVL")
	private Short numberOfOfficersAvailable;

}
