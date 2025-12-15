package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BuildingDto;
import com.example.demo.service.BuildingService;

@RestController
public class BuildingController {

	@Autowired
	BuildingService buildingService;

	@PostMapping("addBuildingWithHostelId")
	public ResponseEntity<String> addBuildingWithHostelId(@RequestBody BuildingDto buildingDto) {
		buildingService.addBuildingWithHostelId(buildingDto);
		return new ResponseEntity<String>("Building Added", HttpStatus.CREATED);
	}

	@GetMapping("getAllBuildings")
	public List<BuildingDto> getAllBuildings() {
		return buildingService.getAllBuildings();
	}

	 @GetMapping("/buildings/hostel/{hostelId}")
	    public List<BuildingDto> getBuildingsByHostelId(@PathVariable int hostelId) {
	        return buildingService.getBuildingsByHostelId(hostelId);
	    }

}
