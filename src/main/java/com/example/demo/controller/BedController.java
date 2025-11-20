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

import com.example.demo.dto.BedDTO;
import com.example.demo.entity.Bed;
import com.example.demo.service.BedService;

@RestController
public class BedController {

	@Autowired
	BedService bedService;

	@GetMapping("getAvailableBeds/{sharing}/{hostelId}")
	public List<BedDTO> findAvailableBedsOfTwoSharing(@PathVariable int sharing, @PathVariable int hostelId) {
		return bedService.getAvailableBedsForTwoSharing(sharing, hostelId);
	}

	@PostMapping("addBedWithRoomId")
	public ResponseEntity addBedWithRoomId(@RequestBody BedDTO bedDTO) {
		bedService.addBedWithRoomId(bedDTO);
		return new ResponseEntity("Bed added", HttpStatus.CREATED);
	}

	@GetMapping("getAllBeds")
	public List<Bed> getAllBeds() {
		return bedService.getAllBeds();
	}
}
