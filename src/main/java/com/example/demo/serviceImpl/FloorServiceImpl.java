package com.example.demo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.FloorDto;
import com.example.demo.entity.Building;
import com.example.demo.entity.Floor;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.FloorRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.service.FloorService;

@Service
public class FloorServiceImpl implements FloorService {

	@Autowired
	FloorRepository floorRepository;

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	BuildingRepository buildingRepository;

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

	public void addFloorWithBuildingId(FloorDto floorDto) {
		Building building = buildingRepository.findById(floorDto.getBuildingId()).get();

		Floor floor = new Floor();
		floor.setFloorNo(floorDto.getFloorNo());
		floor.setNoOfRooms(floorDto.getNoOfRooms());

		

		floor.setBuilding(building);
		floorRepository.save(floor);
		building.setFloors(building.getFloors() + 1);

	}

	@Override
	public List<Floor> getAllFloors() {
		return floorRepository.findAll();

	}
}
