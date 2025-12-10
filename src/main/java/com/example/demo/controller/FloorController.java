package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.FloorDto;
import com.example.demo.entity.Floor;
import com.example.demo.service.FloorService;

@RestController
public class FloorController {

	@Autowired
	FloorService floorService;

	@PostMapping("addFloorWithBuildingId")
	public ResponseEntity addFloorWithBuildingId(@RequestBody FloorDto floorDto) {
		floorService.addFloorWithBuildingId(floorDto);
		return new ResponseEntity("Floor Added", HttpStatus.CREATED);
	}

	@GetMapping("getAllFloors")
	public List<Floor> getAllFloors() {
		return floorService.getAllFloors();
	}

//	@PostMapping("floor/{fId}")
//	public ResponseEntity addFloor(@PathVariable int fId, @RequestBody FloorDTO floorDTO) {
//		floorService.addFloor(fId, floorDTO);
//		return new ResponseEntity("Floor Added", HttpStatus.CREATED);
//	}
}
