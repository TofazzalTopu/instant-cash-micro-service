package com.info.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchApiRequest implements Serializable {

	private static final long serialVersionUID = -7713041286281167462L;

	private String bruserid;

	private String brcode;

	private String exchcode;

	private String pinno;
	
	private String ipAddress;

	

}
