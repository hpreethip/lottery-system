package com.demo.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { BadRequestException.class })
	protected ResponseEntity<Object> handleBadRequestException(RuntimeException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(ex, request);
		errorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
		return handleExceptionInternal(ex, errorDetails, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
}
