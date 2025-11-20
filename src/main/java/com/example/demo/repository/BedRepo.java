package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Bed;

public interface BedRepo extends JpaRepository<Bed, Integer> {
	
	//JPQL Query
	@Query("SELECT b FROM Bed b WHERE b.status = 'Available' AND b.room.sharing = :sharing AND b.room.floor.building.hostel.id = :hostelId")
	List<Bed> findAvailableBedsOfTwoSharing(@Param("sharing") int sharing, @Param("hostelId") int hostelId);

}
