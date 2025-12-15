package com.example.demo.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.constant.ErrorConstant;
import com.example.demo.dto.BuildingDto;
import com.example.demo.entity.Building;
import com.example.demo.entity.Hostel;
import com.example.demo.exception.BuildingServiceException;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.FloorRepository;
import com.example.demo.repository.HostelRepository;
import com.example.demo.repository.OrganizationRepository;
import com.example.demo.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	BuildingRepository buildingRepository;

	@Autowired
	FloorRepository floorRepository;

	@Autowired
	HostelRepository hostelRepository;

	@Autowired
	OrganizationRepository organizationRepository;

	@Override
	public void addBuildingWithHostelId(BuildingDto buildingDto) {

		if (buildingDto == null) {
			throw new BuildingServiceException(ErrorConstant.BUILDING_DATA_REQUIRED, HttpStatus.BAD_REQUEST);
		}

		if (buildingDto.getHostelId() <= 0) {
			throw new BuildingServiceException(ErrorConstant.INVALID_ID, HttpStatus.BAD_REQUEST);
		}

		Optional<Hostel> hostelOptional = hostelRepository.findById(buildingDto.getHostelId());

		if (hostelOptional.isEmpty()) {
			throw new BuildingServiceException(ErrorConstant.HOSTEL_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		Hostel hostel = hostelOptional.get();

		Building building = new Building();
		building.setName(buildingDto.getName());
		building.setFloors(buildingDto.getFloors());
		building.setWarden(buildingDto.getWarden());

		building.setHostel(hostel);

		try {
			buildingRepository.save(building);
		} catch (Exception e) {
			throw new BuildingServiceException(ErrorConstant.SAVE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<BuildingDto> getAllBuildings() {

		List<Building> buildings = buildingRepository.findAll();

		if (buildings.isEmpty()) {
			throw new BuildingServiceException(ErrorConstant.BUILDING_LIST_EMPTY, HttpStatus.NOT_FOUND);
		}

		List<BuildingDto> buildingDtos = new ArrayList<>();

		for (Building building : buildings) {

			BuildingDto buildingDto = new BuildingDto();

			buildingDto.setId(building.getId());
			buildingDto.setHostelId(building.getHostel().getId());
			buildingDto.setName(building.getName());
			buildingDto.setFloors(building.getFloors());
			buildingDto.setWarden(building.getWarden());

			buildingDtos.add(buildingDto);
		}
		return buildingDtos;

	}

	@Override
	public List<BuildingDto> getBuildingsByHostelId(int hostelId) {

		if (hostelId <= 0) {
			throw new BuildingServiceException(ErrorConstant.INVALID_ID, HttpStatus.BAD_REQUEST);
		}

		List<Building> buildings = buildingRepository.findByHostel_Id(hostelId);

		if (buildings.isEmpty()) {
			throw new BuildingServiceException(ErrorConstant.BUILDING_LIST_EMPTY, HttpStatus.NOT_FOUND);
		}

		List<BuildingDto> buildingDtos = new ArrayList<>();

		for (Building building : buildings) {

			BuildingDto buildingDto = new BuildingDto();

			buildingDto.setId(building.getId());

			buildingDto.setHostelId(building.getHostel().getId());
			buildingDto.setName(building.getName());
			buildingDto.setFloors(building.getFloors());
			buildingDto.setWarden(building.getWarden());

			buildingDtos.add(buildingDto);

		}
		return buildingDtos;
	}

}
