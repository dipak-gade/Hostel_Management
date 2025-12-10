package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.RoomDto;
import com.example.demo.entity.Room;

public interface RoomService {

	public void addRoomWithFloorId(RoomDto roomDto);

//	public void addRoomWithBeds(RoomDTO roomDTO);

	public List<Room> getAllRooms();
}
