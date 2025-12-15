package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
	List<Booking> findByOrganizationId(int organizationId);
	
}
