package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class AuthServiceException extends RuntimeException {
	private final HttpStatus httpStatus;

	public AuthServiceException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
