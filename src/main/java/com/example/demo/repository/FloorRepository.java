package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Floor;

public interface FloorRepository extends JpaRepository<Floor, Integer> {
	List<Floor> findByBuilding_Id(int buildingId);

}
