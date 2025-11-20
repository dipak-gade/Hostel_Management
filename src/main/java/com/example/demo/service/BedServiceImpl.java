package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BedDTO;
import com.example.demo.entity.Bed;
import com.example.demo.entity.Building;
import com.example.demo.entity.Floor;
import com.example.demo.entity.Hostel;
import com.example.demo.entity.Room;
import com.example.demo.repository.BedRepo;
import com.example.demo.repository.RoomRepo;

@Service
public class BedServiceImpl implements BedService {

	@Autowired
	BedRepo bedRepo;

	@Autowired
	RoomRepo roomRepo;

	@Override
	public List<BedDTO> getAvailableBedsForTwoSharing(int sharing, int hostelId) {

		List<Bed> beds = bedRepo.findAvailableBedsOfTwoSharing(sharing, hostelId);

		List<BedDTO> listOfBed = new ArrayList();

		for (Bed b : beds) {
			Room room = b.getRoom();
			Floor floor = room.getFloor();
			Building building = floor.getBuilding();
			Hostel hostel = building.getHostel();

			BedDTO bedDTO = new BedDTO();

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

	@Override
	public void addBedWithRoomId(BedDTO bedDTO) {
		Room room = roomRepo.findById(bedDTO.getRoomId()).get();

		Bed bed = new Bed();
		bed.setBedNo(bedDTO.getBedNo());
		bed.setStatus(bedDTO.getStatus());
		bed.setPrice(bedDTO.getPrice());

		bed.setRoom(room);

		bedRepo.save(bed);

	}

	@Override
	public List<Bed> getAllBeds() {
		return bedRepo.findAll();
	}

}
