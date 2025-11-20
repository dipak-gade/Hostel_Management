package com.example.demo.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.exception.InvalidDataException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(exception = InvalidDataException.class)
	public ResponseEntity handleInvalidDataException(InvalidDataException ie) {
		return new ResponseEntity(ie.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
