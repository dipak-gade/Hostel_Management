package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OrganizationDto;
import com.example.demo.service.OrganizationService;

@RestController
public class OrganizationController {

	@Autowired
	OrganizationService organizationService;

	@PostMapping("addOrganization")
	public ResponseEntity<String> addOrganization(@RequestBody OrganizationDto organizationDto) {
		organizationService.addOrganization(organizationDto);
		return new ResponseEntity<String>("Organization Added", HttpStatus.CREATED);
	}

	@GetMapping("getOrganizationById/{id}")
	public OrganizationDto getOrganizationById(@PathVariable int id) {
		return organizationService.getOrganizationById(id);
	}

	@GetMapping("getAllOrganizations")
	public List<OrganizationDto> getAllOrganization() {
		return organizationService.getAllOrganizations();
	}

	@DeleteMapping("deleteOrganizationById/{id}")
	public ResponseEntity<String> deleteOrganizationById(@PathVariable int id) {
		organizationService.deleteOrganizationById(id);
		return new ResponseEntity<String>("Organization deleted", HttpStatus.OK);
	}

	@PutMapping("updateOrganizationById/{id}")
	public ResponseEntity<String> updateOrganizationById(@PathVariable int id,
			@RequestBody OrganizationDto organizationDto) {
		organizationService.updateOrganizationById(id, organizationDto);
		return new ResponseEntity<String>("Organization updated", HttpStatus.OK);
	}

}
