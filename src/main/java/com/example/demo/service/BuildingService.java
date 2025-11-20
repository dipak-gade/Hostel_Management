package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.BuildingDTO;
import com.example.demo.entity.Building;

public interface BuildingService {

//	public void addBuilding(int bId,BuildingDTO buildingDTO);

	public void addBuildingWithHostelId(BuildingDTO buildingDTO);

	public List<Building> getAllBuildings();
}
