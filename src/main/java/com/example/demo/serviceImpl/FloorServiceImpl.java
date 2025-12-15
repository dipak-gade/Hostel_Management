package com.example.demo.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.constant.ErrorConstant;
import com.example.demo.dto.FloorDto;
import com.example.demo.entity.Building;
import com.example.demo.entity.Floor;
import com.example.demo.exception.FloorServiceException;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.FloorRepository;
import com.example.demo.service.FloorService;

@Service
public class FloorServiceImpl implements FloorService {

	@Autowired
	FloorRepository floorRepository;

	@Autowired
	BuildingRepository buildingRepository;

	public void addFloorWithBuildingId(FloorDto floorDto) {

		if (floorDto == null) {
			throw new FloorServiceException(ErrorConstant.FLOOR_DATA_REQUIRED, HttpStatus.BAD_REQUEST);
		}
		if (floorDto.getBuildingId() <= 0) {
			throw new FloorServiceException(ErrorConstant.INVALID_ID, HttpStatus.BAD_REQUEST);
		}

		Optional<Building> buildingOptional = buildingRepository.findById(floorDto.getBuildingId());

		if (buildingOptional.isEmpty()) {
			throw new FloorServiceException(ErrorConstant.BUILDING_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		Building building = buildingOptional.get();

		Floor floor = new Floor();

		floor.setFloorNo(floorDto.getFloorNo());
		floor.setNoOfRooms(floorDto.getNoOfRooms());

		floor.setBuilding(building);
		try {
			floorRepository.save(floor);
			building.setFloors(building.getFloors() + 1);
			buildingRepository.save(building);

		} catch (Exception e) {
			throw new FloorServiceException(ErrorConstant.SAVE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<FloorDto> getAllFloors() {
		List<Floor> floors = floorRepository.findAll();

		if (floors.isEmpty()) {
			throw new FloorServiceException(ErrorConstant.FLOOR_LIST_EMPTY, HttpStatus.NOT_FOUND);
		}

		List<FloorDto> floorDtos = new ArrayList<>();

		for (Floor floor : floors) {
			FloorDto dto = new FloorDto();
			dto.setId(floor.getId());
			dto.setBuildingId(floor.getBuilding().getId());
			dto.setFloorNo(floor.getFloorNo());
			dto.setNoOfRooms(floor.getNoOfRooms());
			floorDtos.add(dto);
		}

		return floorDtos;

	}

	@Override
	public List<FloorDto> getFloorsByBuildingId(int buildingId) {

		if (buildingId <= 0) {
			throw new FloorServiceException(ErrorConstant.INVALID_ID, HttpStatus.BAD_REQUEST);
		}

		List<Floor> floors = floorRepository.findByBuilding_Id(buildingId);

		if (floors.isEmpty()) {
			throw new FloorServiceException(ErrorConstant.FLOOR_LIST_EMPTY, HttpStatus.NOT_FOUND);
		}

		List<FloorDto> floorDtos = new ArrayList<>();

		for (Floor floor : floors) {
			FloorDto dto = new FloorDto();
			dto.setId(floor.getId());
			dto.setBuildingId(floor.getBuilding().getId());
			dto.setFloorNo(floor.getFloorNo());
			dto.setNoOfRooms(floor.getNoOfRooms());
			floorDtos.add(dto);
		}

		return floorDtos;
	}

}
