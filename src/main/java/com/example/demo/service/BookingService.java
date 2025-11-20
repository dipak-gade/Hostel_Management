package com.example.demo.service;

public interface BookingService {

	public void createBooking(int userId, int bedId, int finalAmt);

	public void updateBooking(int bookingId, long transactionId, boolean success, long orderId);
}
