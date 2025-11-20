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
	public void createBooking(int userId, int bedId, int finalAmt) {

		Bed bed = bedRepo.findById(bedId).get();
		User user = userRepo.findById(userId).get();

		Booking booking = new Booking();
		booking.setUserId(user.getId());
		booking.setBedId(bed.getId());
		booking.setFinalAmt(finalAmt);
		booking.setStartDate(LocalDateTime.now());
		booking.setBookingStatus("IN_PROGRESS");
		booking.setPaymentStatus("PENDING");

		bookingRepo.save(booking);

	}

	@Override
	public void updateBooking(int bookingId, long transactionId, boolean success, long orderId) {
		Booking booking = bookingRepo.findById(bookingId).get();

		if (success) {
			booking.setBookingStatus("Success");
			booking.setPaymentStatus("Completed");

			// To asssign the bedid to user
			Bed bed = bedRepo.findById(booking.getBedId()).get();
			User user = userRepo.findById(booking.getUserId()).get();
			user.setBed(bed);

			userRepo.save(user);

			// To change the bed status
			bed.setStatus("Occupied");
			bedRepo.save(bed);

		} else {
			booking.setBookingStatus("Failed");
			booking.setPaymentStatus("Failed");
		}

		booking.setOrderId(orderId);
		booking.setTransactionId(transactionId);

		booking.setPaymentDate(LocalDateTime.now());
		booking.setEndDate(LocalDateTime.now());

		bookingRepo.save(booking);

	}

}
