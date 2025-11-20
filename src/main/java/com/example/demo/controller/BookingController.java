package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Booking;
import com.example.demo.service.BookingService;

@RestController
public class BookingController {

	@Autowired
	BookingService bookingService;

	@PostMapping("booking/start{userId}/{bedId}/{amount}")
	public ResponseEntity startBooking(@PathVariable int userId, @PathVariable Integer bedId, @PathVariable int amount) {

		bookingService.createBooking(userId, bedId, amount);
		return new ResponseEntity("Booking Started", HttpStatus.CREATED);
	}

	@PostMapping("booking/update/{bookingId}/{transactionId}/{success}/{orderId}")
	public ResponseEntity updateBooking(@PathVariable int bookingId, @PathVariable int transactionId, boolean success,
			@PathVariable long orderId) {
		bookingService.updateBooking(bookingId, transactionId, success, orderId);
		return new ResponseEntity("Booking completed", HttpStatus.OK);
	}
}
