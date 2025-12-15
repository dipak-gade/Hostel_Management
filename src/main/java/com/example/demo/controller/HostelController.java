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

import com.example.demo.dto.HostelDto;
import com.example.demo.service.HostelService;

@RestController
public class HostelController {

	@Autowired
	HostelService hostelService;

	@PostMapping("addHostelWithOrgId")
	public ResponseEntity<String> addHostelWithOrgId(@RequestBody HostelDto hostelDto) {
		hostelService.addHostelWithOrgId(hostelDto);
		return new ResponseEntity<String>("Hostel added", HttpStatus.CREATED);
	}

	@GetMapping("getHostelById/{id}")
	public HostelDto getHostelById(@PathVariable int id) {
		return hostelService.getHostelById(id);
	}

	@GetMapping("getAllHostels")
	public List<HostelDto> getAllHostels() {
		return hostelService.getAllHostels();
	}

	@GetMapping("getHostelsByOrgId/{orgId}")
	public List<HostelDto> getHostelsByOrgId(@PathVariable int orgId) {
		return hostelService.getHostelsByOrgId(orgId);

	}

}
