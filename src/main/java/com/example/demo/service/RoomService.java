package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.RoomDto;

public interface RoomService {

	public void addRoomWithFloorId(RoomDto roomDto);

	public void createRoomWithBeds(RoomDto roomDTO);

	public List<RoomDto> getAllRooms();

	List<RoomDto> getRoomsByFloorId(int floorId);

}
