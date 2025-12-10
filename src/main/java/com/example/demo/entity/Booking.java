package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int userId;
	private int bedId;
	private int organizationId;

	private int finalAmt;

	private LocalDateTime startDate;
	private String bookingStatus;
	private String paymentStatus;

	private String transactionId;
	private LocalDateTime endDate;
	private LocalDateTime paymentDate;

	@Column(nullable = true)
	private String orderId;

}
