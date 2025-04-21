package com.info.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@BatchSize(size=50)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MBKBRN")
public class MbkBrn implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private MbkBrnKey mbkbrnKey;

	@Column(name = "MBKBRN_NAME")
	private String branchName;

	@Column(name = "MBKBRN_CONC_NAME")
	private String branhConcName;


}
