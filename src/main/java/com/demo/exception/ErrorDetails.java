package com.demo.exception;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.springframework.web.context.request.WebRequest;

import lombok.Data;

@Data
public class ErrorDetails {

	private int status;

	private String message;

	private ZonedDateTime timestamp;
	
	private String details;

	private String exception;
	
	public ErrorDetails(Exception ex, WebRequest webRequest) {
		message = ex.getMessage();
		timestamp = ZonedDateTime.now(ZoneOffset.UTC);
		details = webRequest.getDescription(false);
		exception = ex.getClass().getName();
	}
	
}
