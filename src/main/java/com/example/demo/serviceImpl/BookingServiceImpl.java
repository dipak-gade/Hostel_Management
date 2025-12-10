package com.example.demo.serviceImpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Bed;
import com.example.demo.entity.Booking;
import com.example.demo.entity.User;
import com.example.demo.repository.BedRepository;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	BedRepository bedRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public Booking createBooking(int userId, int bedId, int finalAmt) {
		try {
			Bed bed = bedRepository.findById(bedId).orElseThrow(() -> new RuntimeException("Bed not found: " + bedId));
			User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found: " + userId));

			Booking booking = new Booking();
			booking.setUserId(user.getId());
			booking.setBedId(bed.getId());
			booking.setOrganizationId(bed.getRoom().getFloor().getBuilding().getHostel().getOrganization().getId());
			booking.setFinalAmt(finalAmt);
			booking.setStartDate(LocalDateTime.now());
			booking.setBookingStatus("IN_PROGRESS");
			booking.setPaymentStatus("PENDING");

			return bookingRepository.save(booking);

		} catch (Exception e) {
			e.printStackTrace(); // <-- SHOW THE REAL ERROR
			throw e; // bubble up
		}
	}

	@Override
	public void updateBooking(int bookingId, String transactionId, boolean success, String orderId) {

		Booking booking = bookingRepository.findById(bookingId).orElseThrow();

		booking.setOrderId(orderId);
		booking.setTransactionId(transactionId);
		booking.setPaymentDate(LocalDateTime.now());
		booking.setEndDate(LocalDateTime.now());

		if (success) {
			booking.setBookingStatus("SUCCESS");
			booking.setPaymentStatus("COMPLETED");

			// Assign bed to user
			Bed bed = bedRepository.findById(booking.getBedId()).orElseThrow();
			User user = userRepository.findById(booking.getUserId()).orElseThrow();

			user.setBed(bed);
			userRepository.save(user);

			// Mark bed as occupied
			bed.setStatus("Occupied");
			bedRepository.save(bed);

		} else {
			booking.setBookingStatus("FAILED");
			booking.setPaymentStatus("FAILED");
		}

		bookingRepository.save(booking);
	}
}
