package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.FloorDTO;
import com.example.demo.entity.Building;
import com.example.demo.entity.Floor;
import com.example.demo.repository.BuildingRepo;
import com.example.demo.repository.FloorRepo;
import com.example.demo.repository.RoomRepo;

@Service
public class FloorServiceImpl implements FloorService {

	@Autowired
	FloorRepo floorRepo;

	@Autowired
	RoomRepo roomRepo;

	@Autowired
	BuildingRepo buildingRepo;

//	@Override
//	public void addFloor(int fId, FloorDTO floorDTO) {
//		Floor floor = floorRepo.findById(fId).get();
//
//		List<Room> rooms = floorDTO.getRooms();
//		for (Room r : rooms) {
//			r.setFloor(floor);
//			roomRepo.save(r);
//		}
//
//	}

	public void addFloorWithBuildingId(FloorDTO floorDTO) {
		Building building = buildingRepo.findById(floorDTO.getBuildingId()).get();

		Floor floor = new Floor();
		floor.setFloorNo(floorDTO.getFloorNo());
		floor.setNoOfRooms(floorDTO.getNoOfRooms());

		

		floor.setBuilding(building);
		floorRepo.save(floor);
		building.setFloors(building.getFloors() + 1);

	}

	@Override
	public List<Floor> getAllFloors() {
		return floorRepo.findAll();

	}
}
