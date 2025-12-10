package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class FloorServiceException extends RuntimeException {

	private final HttpStatus httpStatus;

	public FloorServiceException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
