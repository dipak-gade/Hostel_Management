package com.example.demo.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.constant.ErrorConstant;
import com.example.demo.entity.Bed;
import com.example.demo.entity.Booking;
import com.example.demo.entity.User;
import com.example.demo.exception.BookingServiceException;
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

		if (userId <= 0 || bedId <= 0 || finalAmt <= 0) {
			throw new BookingServiceException(ErrorConstant.INVALID_ID, HttpStatus.BAD_REQUEST);
		}

		Optional<Bed> bedOptional = bedRepository.findById(bedId);

		if (bedOptional.isEmpty()) {
			throw new BookingServiceException(ErrorConstant.BED_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		Bed bed = bedOptional.get();

		Optional<User> userOptional = userRepository.findById(userId);

		if (userOptional.isEmpty()) {
			throw new BookingServiceException(ErrorConstant.USER_DATA_REQUIRED, HttpStatus.NOT_FOUND);
		}

		User user = userOptional.get();

		try {
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
			throw new BookingServiceException(ErrorConstant.SAVE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void updateBooking(int bookingId, String transactionId, boolean success, String orderId) {

		if (bookingId <= 0) {
			throw new BookingServiceException(ErrorConstant.INVALID_ID, HttpStatus.BAD_REQUEST);
		}

		Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);

		if (bookingOptional.isEmpty()) {
			throw new BookingServiceException(ErrorConstant.ID_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		Booking booking = bookingOptional.get();

		booking.setOrderId(orderId);
		booking.setTransactionId(transactionId);
		booking.setPaymentDate(LocalDateTime.now());
		booking.setEndDate(LocalDateTime.now());

		if (success) {
			booking.setBookingStatus("SUCCESS");
			booking.setPaymentStatus("COMPLETED");

			// Assign bed to user
			Optional<Bed> bedOptional = bedRepository.findById(booking.getBedId());
			if (bedOptional.isEmpty()) {
				throw new BookingServiceException(ErrorConstant.BED_NOT_FOUND, HttpStatus.NOT_FOUND);
			}
			Bed bed = bedOptional.get();

			Optional<User> userOptional = userRepository.findById(booking.getUserId());
			if (userOptional.isEmpty()) {
				throw new BookingServiceException(ErrorConstant.USER_DATA_REQUIRED, HttpStatus.NOT_FOUND);
			}
			User user = userOptional.get();

			user.setBed(bed);
			userRepository.save(user);

			// Mark bed as occupied
			bed.setStatus("Occupied");
			bedRepository.save(bed);

		} else {
			booking.setBookingStatus("FAILED");
			booking.setPaymentStatus("FAILED");
		}

		try {
			bookingRepository.save(booking);
		} catch (Exception e) {
			throw new BookingServiceException(ErrorConstant.SAVE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<Booking> getBookingsByOrgId(int orgId) {
		if (orgId <= 0) {
			throw new BookingServiceException(ErrorConstant.INVALID_ID, HttpStatus.BAD_REQUEST);
		}
		return bookingRepository.findByOrganizationId(orgId);
	}

}
