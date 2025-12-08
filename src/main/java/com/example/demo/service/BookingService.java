package com.example.demo.service;

import com.example.demo.entity.Booking;

public interface BookingService {

	public Booking createBooking(int userId, int bedId, int finalAmt);

	public void updateBooking(int bookingId, String transactionId, boolean success, String orderId);

}
