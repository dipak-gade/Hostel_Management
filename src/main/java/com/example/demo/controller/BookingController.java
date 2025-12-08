package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Booking;
import com.example.demo.service.BookingService;

@RestController
public class BookingController {

	@Autowired
	BookingService bookingService;

	@PostMapping("booking/start/{userId}/{bedId}/{amount}")
	public ResponseEntity<?> startBooking(@PathVariable int userId, @PathVariable int bedId, @PathVariable int amount) {

		Booking booking = bookingService.createBooking(userId, bedId, amount);

		// Return bookingId so FE can use it with Razorpay
		return ResponseEntity.ok(booking);
	}

	// STEP 4 — UPDATE BOOKING AFTER PAYMENT
	@PostMapping("booking/update/{bookingId}/{success}/{orderId}")
	public ResponseEntity<?> updateBooking(@PathVariable int bookingId, @PathVariable boolean success,
			@PathVariable String orderId, @RequestBody String transactionId // Razorpay payment_id
	) {
		try {
			bookingService.updateBooking(bookingId, transactionId, success, orderId);
			return ResponseEntity.ok("Booking updated");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Failed to update booking");
		}
	}

}
