package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RoomDto;
import com.example.demo.entity.Room;
import com.example.demo.service.RoomService;

@RestController
public class RoomController {

	@Autowired
	RoomService roomService;

	@PostMapping("addRoomWithFloorId")
	public ResponseEntity addRoomWithFloorId(@RequestBody RoomDto roomDto) {
		roomService.addRoomWithFloorId(roomDto);
		return new ResponseEntity("Room added", HttpStatus.CREATED);

	}

	@GetMapping("getAllRooms")
	public List<Room> getAllRooms() {
		return roomService.getAllRooms();
	}

//	@PostMapping("addRoomWithBeds")
//	public ResponseEntity addRoomWithBeds(@RequestBody RoomDTO roomDTO) {
//		roomService.addRoomWithBeds(roomDTO);
//		return new ResponseEntity("Beds added with Room", HttpStatus.CREATED);
//	}

//	@PostMapping("room/{roomId}")
//	public ResponseEntity addRoom(@PathVariable int roomId, @RequestBody RoomDTO roomDTO) {
//		roomService.addRoom(roomId, roomDTO);
//		return new ResponseEntity("Room added", HttpStatus.CREATED);
//	}

}
