package com.example.demo.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.OrganizationDTO;
import com.example.demo.entity.Hostel;
import com.example.demo.entity.Organization;
import com.example.demo.entity.User;
import com.example.demo.exception.InvalidDataException;
import com.example.demo.repository.HostelRepo;
import com.example.demo.repository.OrganizationRepo;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	OrganizationRepo organizationRepo;

	@Autowired
	HostelRepo hostelRepo;

	@Override
	public void addHostelsInOrganozation(OrganizationDTO organizationDTO) {
		try {

			if (organizationDTO == null) {
				throw new InvalidDataException("Organization data cannot be null");
			}
			if (organizationDTO.getName() == null) {
				throw new InvalidDataException("Organization name is required");
			}
			if (organizationDTO.getEmail() == null) {
				throw new InvalidDataException("Organization email is required");
			}

			Organization organization = new Organization();
			organization.setName(organizationDTO.getName());
			organization.setEmail(organizationDTO.getEmail());
			organization.setOwnerName(organizationDTO.getOwnerName());

			Organization org = organizationRepo.save(organization);

			Set<Hostel> hostels = organizationDTO.getHostels();

			for (Hostel h : hostels) {

				h.setOrganization(org);

				hostelRepo.save(h);

			}
		} catch (InvalidDataException ie) {
			throw new InvalidDataException(ie.getMessage());
		}
	}

	@Override
	public List<Organization> getAllOrganizations() {

		return organizationRepo.findAll();
	}

	@Override
	public void addOrganization(Organization organization) {
		organizationRepo.save(organization);
	}

	@Override
	public Organization getOrganizationById(int id) {
		return organizationRepo.findById(id).get();
	}

	@Override
	public void deleteOrganizationById(int id) {
		organizationRepo.deleteById(id);
	}

	@Override
	public void updateOrganizationById(int id, Organization organization) {

		Organization existingOrganization = organizationRepo.findById(id).get();
		
		existingOrganization.setName(organization.getName());
		existingOrganization.setEmail(organization.getEmail());
		existingOrganization.setOwnerName(organization.getOwnerName());
		
		organizationRepo.save(existingOrganization);

	}

}
