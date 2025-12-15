package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.FloorDto;

public interface FloorService {


	public void addFloorWithBuildingId(FloorDto floorDto);

	public List<FloorDto> getAllFloors();
	
	
	List<FloorDto> getFloorsByBuildingId(int buildingId);
}
