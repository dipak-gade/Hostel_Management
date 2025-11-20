package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.OrganizationDTO;
import com.example.demo.entity.Organization;
import com.example.demo.entity.User;

public interface OrganizationService {

	public void addHostelsInOrganozation(OrganizationDTO organizationDTO);

	public List<Organization> getAllOrganizations();

	public void addOrganization(Organization organization);

	public Organization getOrganizationById(int id);

	public void deleteOrganizationById(int id);

	public void updateOrganizationById(int id,Organization organization);
}
