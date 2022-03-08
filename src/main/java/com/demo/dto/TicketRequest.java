package com.demo.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class TicketRequest {

	@NotNull
	private String drawId;
	
	@NotNull
	private String userId;

}
