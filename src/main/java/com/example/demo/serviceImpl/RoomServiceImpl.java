package com.example.demo.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.constant.ErrorConstant;
import com.example.demo.dto.RoomDto;
import com.example.demo.entity.Bed;
import com.example.demo.entity.Floor;
import com.example.demo.entity.Room;
import com.example.demo.exception.RoomServiceException;
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

		if (roomDto == null) {
			throw new RoomServiceException(ErrorConstant.ROOM_DATA_REQUIRED, HttpStatus.BAD_REQUEST);
		}

		if (roomDto.getFloorId() <= 0) {
			throw new RoomServiceException(ErrorConstant.INVALID_ID, HttpStatus.BAD_REQUEST);
		}

		Optional<Floor> floorOptional = floorRepository.findById(roomDto.getFloorId());
		if (floorOptional.isEmpty()) {
			throw new RoomServiceException(ErrorConstant.FLOOR_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		Floor floor = floorOptional.get();

		Room room = new Room();
		room.setRoomNo(roomDto.getRoomNo());
		room.setSharing(roomDto.getSharing());
		room.setType(roomDto.getType());

		room.setFloor(floor);
		try {
			roomRepository.save(room);
		} catch (Exception e) {
			throw new RoomServiceException(ErrorConstant.SAVE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<RoomDto> getAllRooms() {
		List<Room> rooms = roomRepository.findAll();

		if (rooms.isEmpty()) {
			throw new RoomServiceException(ErrorConstant.ROOM_LIST_EMPTY, HttpStatus.NOT_FOUND);
		}
		List<RoomDto> roomDtos = new ArrayList<>();

		for (Room room : rooms) {
			RoomDto dto = new RoomDto();
			dto.setId(room.getId());
			dto.setFloorId(room.getFloor().getId());
			dto.setRoomNo(room.getRoomNo());
			dto.setSharing(room.getSharing());
			dto.setType(room.getType());
			roomDtos.add(dto);
		}

		return roomDtos;
	}

	@Override
	public List<RoomDto> getRoomsByFloorId(int floorId) {

		if (floorId <= 0) {
			throw new RoomServiceException(ErrorConstant.INVALID_ID, HttpStatus.BAD_REQUEST);
		}

		List<Room> rooms = roomRepository.findByFloor_Id(floorId);
		if (rooms.isEmpty()) {
			throw new RoomServiceException(ErrorConstant.ROOM_LIST_EMPTY, HttpStatus.NOT_FOUND);
		}

		List<RoomDto> roomDtos = new ArrayList<>();

		for (Room room : rooms) {

			RoomDto dto = new RoomDto();

			dto.setId(room.getId());
			dto.setFloorId(room.getFloor().getId());
			dto.setRoomNo(room.getRoomNo());
			dto.setSharing(room.getSharing());
			dto.setType(room.getType());
			roomDtos.add(dto);
		}

		return roomDtos;

	}

	@Override
	public void createRoomWithBeds(RoomDto roomDto) {

		if (roomDto == null) {
			throw new RoomServiceException(ErrorConstant.ROOM_DATA_REQUIRED, HttpStatus.BAD_REQUEST);
		}

		if (roomDto.getFloorId() <= 0) {
			throw new RoomServiceException(ErrorConstant.INVALID_ID, HttpStatus.BAD_REQUEST);
		}
		Optional<Floor> floorOptional = floorRepository.findById(roomDto.getFloorId());

		if (floorOptional.isEmpty()) {
			throw new RoomServiceException(ErrorConstant.FLOOR_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		Floor floor = floorOptional.get();

		Room room = new Room();
		room.setRoomNo(roomDto.getRoomNo());
		room.setSharing(roomDto.getSharing());
		room.setType(roomDto.getType());
		room.setFloor(floor);

		roomRepository.save(room);

//		List<Bed> beds = roomDto.getBeds();

		if (roomDto.getBeds().isEmpty()) {
			throw new RoomServiceException(ErrorConstant.BED_LIST_EMPTY, HttpStatus.BAD_REQUEST);
		}
		for (Bed b : roomDto.getBeds()) {

			b.setRoom(room);
			bedRepository.save(b);

		}

	}
}
