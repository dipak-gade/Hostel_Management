package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Bed;
import com.example.demo.entity.Booking;
import com.example.demo.entity.User;
import com.example.demo.repository.BedRepo;
import com.example.demo.repository.BookingRepo;
import com.example.demo.repository.UserRepo;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	BookingRepo bookingRepo;

	@Autowired
	BedRepo bedRepo;

	@Autowired
	UserRepo userRepo;

	@Override
	public Booking createBooking(int userId, int bedId, int finalAmt) {
		try {
			Bed bed = bedRepo.findById(bedId).orElseThrow(() -> new RuntimeException("Bed not found: " + bedId));
			User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found: " + userId));

			Booking booking = new Booking();
			booking.setUserId(user.getId());
			booking.setBedId(bed.getId());
			booking.setOrganizationId(bed.getRoom().getFloor().getBuilding().getHostel().getOrganization().getId());
			booking.setFinalAmt(finalAmt);
			booking.setStartDate(LocalDateTime.now());
			booking.setBookingStatus("IN_PROGRESS");
			booking.setPaymentStatus("PENDING");

			return bookingRepo.save(booking);

		} catch (Exception e) {
			e.printStackTrace(); // <-- SHOW THE REAL ERROR
			throw e; // bubble up
		}
	}

	@Override
	public void updateBooking(int bookingId, String transactionId, boolean success, String orderId) {

		Booking booking = bookingRepo.findById(bookingId).orElseThrow();

		booking.setOrderId(orderId);
		booking.setTransactionId(transactionId);
		booking.setPaymentDate(LocalDateTime.now());
		booking.setEndDate(LocalDateTime.now());

		if (success) {
			booking.setBookingStatus("SUCCESS");
			booking.setPaymentStatus("COMPLETED");

			// Assign bed to user
			Bed bed = bedRepo.findById(booking.getBedId()).orElseThrow();
			User user = userRepo.findById(booking.getUserId()).orElseThrow();

			user.setBed(bed);
			userRepo.save(user);

			// Mark bed as occupied
			bed.setStatus("Occupied");
			bedRepo.save(bed);

		} else {
			booking.setBookingStatus("FAILED");
			booking.setPaymentStatus("FAILED");
		}

		bookingRepo.save(booking);
	}
}
