package com.demo.dto;

import java.util.Date;

import lombok.Data;

@Data
public class DrawResponse {
	
	private String drawId;

	private String name;
	
	private String status;

	private Date startDate;

	private Date endDate;

	private String winnerUserId;
	
}
