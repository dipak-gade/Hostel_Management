package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.FloorDto;
import com.example.demo.entity.Floor;

public interface FloorService {

//	public void addFloor(int fId,FloorDto floorDto);

	public void addFloorWithBuildingId(FloorDto floorDto);

	public List<Floor> getAllFloors();
}
