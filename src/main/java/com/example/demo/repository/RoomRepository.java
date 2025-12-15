package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
	 List<Room> findByFloor_Id(int floorId);
}
