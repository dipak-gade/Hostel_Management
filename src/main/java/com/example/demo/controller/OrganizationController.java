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

import com.example.demo.dto.OrganizationDTO;
import com.example.demo.entity.Organization;
import com.example.demo.entity.User;
import com.example.demo.service.OrganizationService;

@RestController
public class OrganizationController {

	@Autowired
	OrganizationService organizationService;

	@PostMapping("addhostelsWithOrg")
	public ResponseEntity addHostelsInOrganozation(@RequestBody OrganizationDTO organizationDTO) {
		organizationService.addHostelsInOrganozation(organizationDTO);
		return new ResponseEntity("Organization Added", HttpStatus.CREATED);
	}

	@GetMapping("getAllOrganizations")
	public List<Organization> getAllOrganization() {
		return organizationService.getAllOrganizations();
	}

	@PostMapping("addOrganization")
	public ResponseEntity addOrganization(@RequestBody Organization organization) {
		organizationService.addOrganization(organization);
		return new ResponseEntity("Organization Added", HttpStatus.CREATED);
	}

	@GetMapping("getOrganizationById/{id}")
	public Organization getOrganizationById(@PathVariable int id) {
		return organizationService.getOrganizationById(id);
	}

	@DeleteMapping("deleteOrganizationById/{id}")
	public ResponseEntity deleteOrganizationById(@PathVariable int id) {
		organizationService.deleteOrganizationById(id);
		return new ResponseEntity("Organization deleted", HttpStatus.OK);
	}
	
	@PutMapping("updateOrganizationById/{id}")
	public ResponseEntity updateOrganizationById(@PathVariable int id,@RequestBody Organization organization) {
		organizationService.updateOrganizationById(id, organization);
		return new ResponseEntity("Organization updated",HttpStatus.OK);
	}

}
