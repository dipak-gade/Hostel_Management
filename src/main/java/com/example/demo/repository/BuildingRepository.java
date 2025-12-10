package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Building;

public interface BuildingRepository extends JpaRepository<Building, Integer> {

}
