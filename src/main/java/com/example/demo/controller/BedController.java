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

import com.example.demo.dto.BedDto;
import com.example.demo.service.BedService;

@RestController
public class BedController {

	@Autowired
	BedService bedService;

	@PostMapping("addBedWithRoomId")
	public ResponseEntity<String> addBedWithRoomId(@RequestBody BedDto bedDto) {
		bedService.addBedWithRoomId(bedDto);
		return new ResponseEntity<String>("Bed added", HttpStatus.CREATED);
	}

	@GetMapping("getAllBeds")
	public List<BedDto> getAllBeds() {
		return bedService.getAllBeds();
	}

	@GetMapping("/room/{roomId}")
	public List<BedDto> getBedsByRoomId(@PathVariable int roomId) {
		return bedService.getBedsByRoomId(roomId);
	}

	@GetMapping("getAvailableBeds/{sharing}/{hostelId}")
	public List<BedDto> findAvailableBedsOfTwoSharing(@PathVariable int sharing, @PathVariable int hostelId) {
		return bedService.getAvailableBedsForRoomSharing(sharing, hostelId);
	}
}
