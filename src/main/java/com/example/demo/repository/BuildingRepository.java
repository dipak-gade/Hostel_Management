package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Building;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
	List<Building> findByHostel_Id(int hostelId);

}
