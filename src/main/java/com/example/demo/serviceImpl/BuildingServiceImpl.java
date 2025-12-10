package com.example.demo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BuildingDto;
import com.example.demo.entity.Building;
import com.example.demo.entity.Hostel;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.FloorRepository;
import com.example.demo.repository.HostelRepository;
import com.example.demo.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	BuildingRepository buildingRepository;

	@Autowired
	FloorRepository floorRepository;

	@Autowired
	HostelRepository hostelRepository;


	@Override
	public void addBuildingWithHostelId(BuildingDto buildingDto) {
		Hostel hostel = hostelRepository.findById(buildingDto.getHostelId()).get();

		Building building = new Building();
		building.setName(buildingDto.getName());
		building.setFloors(buildingDto.getFloors());
		building.setWarden(buildingDto.getWarden());

		building.setHostel(hostel);

		buildingRepository.save(building);
	}

	@Override
	public List<Building> getAllBuildings() {
		return buildingRepository.findAll();
	}

}
