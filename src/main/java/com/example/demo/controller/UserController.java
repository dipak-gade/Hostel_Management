package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

//	@PostMapping("addStudentWithBedId/{bedId}")
//	public ResponseEntity addStudentWithBedId(@PathVariable int bedId, @RequestBody StudentDTO studentDTO) {
//		studentService.addStudentWithBedId(bedId, studentDTO);
//		return new ResponseEntity("Student Added", HttpStatus.CREATED);
//	}

	@PostMapping("addUserWithoutBedId")
	public ResponseEntity addUser(@RequestBody User user) {
		userService.addUser(user);
		return new ResponseEntity("User Added", HttpStatus.CREATED);
	}
}
