package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class BedServiceException extends RuntimeException {

	private final HttpStatus httpStatus;

	public BedServiceException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
