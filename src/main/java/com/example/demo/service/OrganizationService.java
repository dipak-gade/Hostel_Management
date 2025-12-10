package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.OrganizationDto;

public interface OrganizationService {

//	public void addHostelsInOrganozation(OrganizationDto organizationDto);

	public List<OrganizationDto> getAllOrganizations();

	public void addOrganization(OrganizationDto organizationDto);

	public OrganizationDto getOrganizationById(int id);

	public void deleteOrganizationById(int id);

	public void updateOrganizationById(int id, OrganizationDto organizationDto);
}
