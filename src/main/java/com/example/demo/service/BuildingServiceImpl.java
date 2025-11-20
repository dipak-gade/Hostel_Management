package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BuildingDTO;
import com.example.demo.entity.Building;
import com.example.demo.entity.Hostel;
import com.example.demo.repository.BuildingRepo;
import com.example.demo.repository.FloorRepo;
import com.example.demo.repository.HostelRepo;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	BuildingRepo buildingRepo;

	@Autowired
	FloorRepo floorRepo;

	@Autowired
	HostelRepo hostelRepo;

//	@Override
//	public void addBuilding(int bId, BuildingDTO buildingDTO) {
//		Building building = buildingRepo.findById(bId).get();
//
//		List<Floor> floors = buildingDTO.getFloors();
//		for (Floor f : floors) {
//
//			f.setBuilding(building);
//			floorRepo.save(f);
//		}
//
//	}

	@Override
	public void addBuildingWithHostelId(BuildingDTO buildingDTO) {
		Hostel hostel = hostelRepo.findById(buildingDTO.getHostelId()).get();

		Building building = new Building();
		building.setName(buildingDTO.getName());
		building.setFloors(buildingDTO.getFloors());
		building.setWarden(buildingDTO.getWarden());

		building.setHostel(hostel);

		buildingRepo.save(building);
	}

	@Override
	public List<Building> getAllBuildings() {
		return buildingRepo.findAll();
	}

}
