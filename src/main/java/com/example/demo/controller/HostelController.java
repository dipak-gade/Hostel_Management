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
import com.example.demo.entity.Hostel;
import com.example.demo.service.HostelService;

@RestController
public class HostelController {

	@Autowired
	HostelService hostelService;

	@PostMapping("addHostelWithOrgId")
	public ResponseEntity addHostelWithOrgId(@RequestBody HostelDto hostelDto) {
		hostelService.addHostelWithOrgId(hostelDto);
		return new ResponseEntity("Hostel added", HttpStatus.CREATED);
	}

	@GetMapping("getHostelById/{id}")
	public Hostel getHostelById(@PathVariable int id) {
		return hostelService.getHostelById(id);
	}

	@GetMapping("getAllHostels")
	public List<Hostel> getAllHostels() {
		return hostelService.getAllHostels();
	}

	@GetMapping("getHostelsByOrgId/{oId}")
	public List<Hostel> getHostelsByOrgId(@PathVariable int oId) {

		return hostelService.getHostelsByOrgId(oId);

	}

//	@PostMapping("buildings/{hId}")
//	public ResponseEntity addBuildingsInHostel(@PathVariable int hId, @RequestBody HostelDTO hostelDTO) {
//		hostelService.addBuildingsInHostel(hId, hostelDTO);
//		return new ResponseEntity("Buildings added in hostel", HttpStatus.CREATED);
//	}

//	@PostMapping("addHostel")
//	public ResponseEntity addHostel(@RequestBody Hostel hostel) {
//		hostelService.addHostel(hostel);
//		return new ResponseEntity("Hostel Added", HttpStatus.CREATED);
//	}
}
