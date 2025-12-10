package com.example.demo.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.exception.*;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(OrganizationServiceException.class)
	public ResponseEntity<String> handleOrganizationException(OrganizationServiceException ex) {
		return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
	}

	@ExceptionHandler(HostelServiceException.class)
	public ResponseEntity<String> handleHostelException(HostelServiceException ex) {
		return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
	}

	@ExceptionHandler(BuildingServiceException.class)
	public ResponseEntity<String> handleBuildingException(BuildingServiceException ex) {
		return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
	}

	@ExceptionHandler(FloorServiceException.class)
	public ResponseEntity<String> handleFloorException(FloorServiceException ex) {
		return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
	}

	@ExceptionHandler(RoomServiceException.class)
	public ResponseEntity<String> handleRoomException(RoomServiceException ex) {
		return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
	}

	@ExceptionHandler(BedServiceException.class)
	public ResponseEntity<String> handleBedException(BedServiceException ex) {
		return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
	}

	@ExceptionHandler(BookingServiceException.class)
	public ResponseEntity<String> handleBookingException(BookingServiceException ex) {
		return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
	}

	@ExceptionHandler(UserServiceException.class)
	public ResponseEntity<String> handleUserException(UserServiceException ex) {
		return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
	}

	@ExceptionHandler(BreakupServiceException.class)
	public ResponseEntity<String> handleBreakupException(BreakupServiceException ex) {
		return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
	}

	// Bad Input
	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<String> handleInvalidDataException(InvalidDataException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	// Unexpected errors
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGlobalException(Exception ex) {
		return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
	} 
}
