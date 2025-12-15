package com.example.demo.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.constant.ErrorConstant;
import com.example.demo.dto.BedDto;
import com.example.demo.entity.Bed;
import com.example.demo.entity.Building;
import com.example.demo.entity.Floor;
import com.example.demo.entity.Hostel;
import com.example.demo.entity.Room;
import com.example.demo.exception.BedServiceException;
import com.example.demo.repository.BedRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.service.BedService;

@Service
public class BedServiceImpl implements BedService {

	@Autowired
	BedRepository bedRepository;

	@Autowired
	RoomRepository roomRepository;

	@Override
	public void addBedWithRoomId(BedDto bedDto) {

		if (bedDto == null) {
			throw new BedServiceException(ErrorConstant.BED_DATA_REQUIRED, HttpStatus.BAD_REQUEST);
		}

		if (bedDto.getRoomId() <= 0) {
			throw new BedServiceException(ErrorConstant.INVALID_ID, HttpStatus.BAD_REQUEST);
		}

		Optional<Room> roomOptional = roomRepository.findById(bedDto.getRoomId());

		if (roomOptional.isEmpty()) {
			throw new BedServiceException(ErrorConstant.ROOM_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		Room room = roomOptional.get();

		Bed bed = new Bed();
		bed.setBedNo(bedDto.getBedNo());
		bed.setStatus(bedDto.getStatus());
		bed.setPrice(bedDto.getPrice());

		bed.setRoom(room);

		try {
			bedRepository.save(bed);
		} catch (Exception e) {
			throw new BedServiceException(ErrorConstant.SAVE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<BedDto> getAllBeds() {

		List<Bed> beds = bedRepository.findAll();

		if (beds.isEmpty()) {
			throw new BedServiceException(ErrorConstant.BED_LIST_EMPTY, HttpStatus.NOT_FOUND);
		}

		List<BedDto> bedDtos = new ArrayList<>();

		for (Bed bed : beds) {
			BedDto dto = new BedDto();
			dto.setBedId(bed.getId());
			dto.setBedNo(bed.getBedNo());
			dto.setStatus(bed.getStatus());
			dto.setPrice(bed.getPrice());
			dto.setRoomId(bed.getRoom().getId());
			bedDtos.add(dto);
		}

		return bedDtos;
	}

	@Override
	public List<BedDto> getAvailableBedsForRoomSharing(int sharing, int hostelId) {

		if (sharing <= 0 || hostelId <= 0) {
			throw new BedServiceException(ErrorConstant.INVALID_ID, HttpStatus.BAD_REQUEST);
		}

		List<Bed> beds = bedRepository.findAvailableBedsOfRoomSharing(sharing, hostelId);

		if (beds.isEmpty()) {
			throw new BedServiceException(ErrorConstant.BED_LIST_EMPTY, HttpStatus.NOT_FOUND);
		}

		List<BedDto> bedDtos = new ArrayList<>();

		for (Bed b : beds) {
			Room room = b.getRoom();
			Floor floor = room.getFloor();
			Building building = floor.getBuilding();
			Hostel hostel = building.getHostel();

			BedDto bedDTO = new BedDto();

			bedDTO.setBedNo(b.getBedNo());
			bedDTO.setPrice(b.getPrice());
			bedDTO.setStatus(b.getStatus());
			bedDTO.setBedId(b.getId());

			bedDTO.setRoomNo(room.getRoomNo());
			bedDTO.setRoomId(room.getId());
			bedDTO.setFloorNo(floor.getFloorNo());
			bedDTO.setHostelName(hostel.getName());
			bedDTO.setBuildingName(building.getName());
			bedDTO.setAddress(hostel.getAddress());

			bedDtos.add(bedDTO);

		}
		return bedDtos;
	}

	@Override
	public List<BedDto> getBedsByRoomId(int roomId) {

		if (roomId <= 0) {
			throw new BedServiceException(ErrorConstant.INVALID_ID, HttpStatus.BAD_REQUEST);
		}
		List<Bed> beds = bedRepository.findByRoom_Id(roomId);
		if (beds.isEmpty()) {
			throw new BedServiceException(ErrorConstant.BED_LIST_EMPTY, HttpStatus.NOT_FOUND);
		}

		List<BedDto> bedDtos = new ArrayList<>();

		for (Bed bed : beds) {
			BedDto dto = new BedDto();
			dto.setBedId(bed.getId());
			dto.setBedNo(bed.getBedNo());
			dto.setStatus(bed.getStatus());
			dto.setPrice(bed.getPrice());
			dto.setRoomId(bed.getRoom().getId());
			bedDtos.add(dto);
		}

		return bedDtos;
	}

}
