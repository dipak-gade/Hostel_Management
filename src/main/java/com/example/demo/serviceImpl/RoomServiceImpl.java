package com.example.demo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RoomDto;
import com.example.demo.entity.Floor;
import com.example.demo.entity.Room;
import com.example.demo.repository.BedRepository;
import com.example.demo.repository.FloorRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	BedRepository bedRepository;

	@Autowired
	FloorRepository floorRepository;

	@Override
	public void addRoomWithFloorId(RoomDto roomDto) {
		Floor floor = floorRepository.findById(roomDto.getFloorId()).get();

		Room room = new Room();
		room.setRoomNo(roomDto.getRoomNo());
		room.setSharing(roomDto.getSharing());
		room.setType(roomDto.getType());

		room.setFloor(floor);

		roomRepository.save(room);
	}

	@Override
	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}

//	@Override
//	public void addRoomWithBeds(RoomDTO roomDTO) {
//		Floor floor = floorRepo.findById(roomDTO.getFloorId()).get();
//
//		Room room = new Room();
//		room.setRoomNo(roomDTO.getRoomNo());
//		room.setSharing(roomDTO.getSharing());
//		room.setType(roomDTO.getType());
//		room.setFloor(floor);
//
//		Set<Bed> beds = roomDTO.getBeds();
//		for (Bed b : beds) {
//			roomRepo.save(room);
//			b.setRoom(room);
//			bedRepo.save(b);
//
//		}
//
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
