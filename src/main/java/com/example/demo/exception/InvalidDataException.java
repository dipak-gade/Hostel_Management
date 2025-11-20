package com.example.demo.exception;

public class InvalidDataException extends RuntimeException {

	private String msg;

	@Override
	public String getMessage() {
		return msg;
	}

	public InvalidDataException(String msg) {
		this.msg = msg;
	}

}
