package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.BuildingDto;
import com.example.demo.entity.Building;

public interface BuildingService {

//	public void addBuilding(int bId,BuildingDto buildingDto);

	public void addBuildingWithHostelId(BuildingDto buildingDto);

	public List<Building> getAllBuildings();
}
