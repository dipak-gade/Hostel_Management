package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Floor;

public interface FloorRepo extends JpaRepository<Floor, Integer> {

}
