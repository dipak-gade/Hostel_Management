package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.BuildingDto;

public interface BuildingService {


	public void addBuildingWithHostelId(BuildingDto buildingDto);

	public List<BuildingDto> getAllBuildings();

	public List<BuildingDto> getBuildingsByHostelId(int hostelId);	
}
