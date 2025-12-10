package com.example.demo.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BedDto;
import com.example.demo.entity.Bed;
import com.example.demo.entity.Building;
import com.example.demo.entity.Floor;
import com.example.demo.entity.Hostel;
import com.example.demo.entity.Room;
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
		Room room = roomRepository.findById(bedDto.getRoomId()).get();

		Bed bed = new Bed();
		bed.setBedNo(bedDto.getBedNo());
		bed.setStatus(bedDto.getStatus());
		bed.setPrice(bedDto.getPrice());

		bed.setRoom(room);

		bedRepository.save(bed);

	}

	@Override
	public List<Bed> getAllBeds() {
		return bedRepository.findAll();
	}

	@Override
	public List<BedDto> getAvailableBedsForTwoSharing(int sharing, int hostelId) {

		List<Bed> beds = bedRepository.findAvailableBedsOfTwoSharing(sharing, hostelId);

		List<BedDto> listOfBed = new ArrayList();

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

			listOfBed.add(bedDTO);

		}
		return listOfBed;
	}

}
