package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@PostMapping("addUserWithoutBedId")
	public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {

		log.info("addUser API called");
		log.error("Sample error log");

		userService.addUser(userDto);
		return new ResponseEntity<String>("User Added", HttpStatus.CREATED);
	}
}
