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

import com.example.demo.dto.RoomDto;
import com.example.demo.service.RoomService;

@RestController
public class RoomController {

	@Autowired
	RoomService roomService;

	@PostMapping("addRoomWithFloorId")
	public ResponseEntity<String> addRoomWithFloorId(@RequestBody RoomDto roomDto) {
		roomService.addRoomWithFloorId(roomDto);
		return new ResponseEntity<String>("Room added", HttpStatus.CREATED);

	}

	@GetMapping("getAllRooms")
	public List<RoomDto> getAllRooms() {
		return roomService.getAllRooms();
	}

	@GetMapping("/floor/{floorId}")
	public List<RoomDto> getRoomsByFloorId(@PathVariable int floorId) {
		return roomService.getRoomsByFloorId(floorId);
	}

	@PostMapping("/rooms/create")
	public ResponseEntity<String> addRoomWithBeds(@RequestBody RoomDto roomDto) {
		roomService.createRoomWithBeds(roomDto);
		return new ResponseEntity<String>("Beds added with Room", HttpStatus.CREATED);
	}

}
