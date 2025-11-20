package com.example.demo.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RoomDTO;
import com.example.demo.entity.Bed;
import com.example.demo.entity.Floor;
import com.example.demo.entity.Room;
import com.example.demo.repository.BedRepo;
import com.example.demo.repository.FloorRepo;
import com.example.demo.repository.RoomRepo;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	RoomRepo roomRepo;

	@Autowired
	BedRepo bedRepo;

	@Autowired
	FloorRepo floorRepo;

	@Override
	public void addRoomWithBeds(RoomDTO roomDTO) {
		Floor floor = floorRepo.findById(roomDTO.getFloorId()).get();

		Room room = new Room();
		room.setRoomNo(roomDTO.getRoomNo());
		room.setSharing(roomDTO.getSharing());
		room.setType(roomDTO.getType());
		room.setFloor(floor);

		Set<Bed> beds = roomDTO.getBeds();
		for (Bed b : beds) {
			roomRepo.save(room);
			b.setRoom(room);
			bedRepo.save(b);

		}

	}

	@Override
	public List<Room> getAllRooms() {
		return roomRepo.findAll();
	}

//	@Override
//	public void addRoomWithFloorId(RoomDTO roomDTO) {
//		Floor floor = floorRepo.findById(roomDTO.getFloorId()).get();
//
//		Room room = new Room();
//		room.setRoomNo(roomDTO.getRoomNo());
//		room.setSharing(roomDTO.getSharing());
//		room.setType(roomDTO.getType());
//
//		room.setFloor(floor);
//
//		roomRepo.save(room);
//	}

//	@Override
//	public void addRoom(int roomId, RoomDTO roomDTO) {
//		Room room = roomRepo.findById(roomId).get();
//
//		Set<Bed> beds = roomDTO.getBeds();
//
//		for (Bed b : beds) {
//			b.setRoom(room);
//			bedRepo.save(b);
//		}
//	}

}
