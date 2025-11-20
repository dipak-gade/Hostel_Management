package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.FloorDTO;
import com.example.demo.entity.Floor;

public interface FloorService {

//	public void addFloor(int fId,FloorDTO floorDTO);

	public void addFloorWithBuildingId(FloorDTO floorDTO);

	public List<Floor> getAllFloors();
}
