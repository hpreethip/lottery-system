package com.demo.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserRequest {

	@NotNull
	private String userName;
	
	@NotNull
	private String email;

}
