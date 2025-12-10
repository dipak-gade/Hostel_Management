package com.example.demo.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.constant.ErrorConstant;
import com.example.demo.dto.OrganizationDto;
import com.example.demo.entity.Organization;
import com.example.demo.exception.OrganizationServiceException;
import com.example.demo.repository.HostelRepository;
import com.example.demo.repository.OrganizationRepository;
import com.example.demo.service.OrganizationService;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	OrganizationRepository organizationRepository;

	@Autowired
	HostelRepository hostelRepository;

	@Override
	public void addOrganization(OrganizationDto organizationDto) {

		if (organizationDto == null) {
			throw new OrganizationServiceException(ErrorConstant.ORGANIZATION_DATA_REQUIRED, HttpStatus.BAD_REQUEST);
		}
		if (organizationDto.getName() == null) {
			throw new OrganizationServiceException(ErrorConstant.ORGANIZATION_NAME_REQUIRED, HttpStatus.BAD_REQUEST);
		}
		if (organizationDto.getEmail() == null) {
			throw new OrganizationServiceException(ErrorConstant.ORGANIZATION_EMAIL_REQUIRED, HttpStatus.BAD_REQUEST);
		}

		try {
			Organization organization = new Organization();
			organization.setName(organizationDto.getName());
			organization.setEmail(organizationDto.getEmail());
			organization.setOwnerName(organizationDto.getOwnerName());
			organization.setContact(organizationDto.getContact());

			organizationRepository.save(organization);
		} catch (Exception e) {
			throw new OrganizationServiceException(ErrorConstant.SAVE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public OrganizationDto getOrganizationById(int id) {

		if (id <= 0) {
			throw new OrganizationServiceException(ErrorConstant.ID_NOT_FOUND, HttpStatus.BAD_REQUEST);
		}

		Optional<Organization> organizationOptional = organizationRepository.findById(id);

		if (organizationOptional.isEmpty()) {
			throw new OrganizationServiceException(ErrorConstant.ORGANIZATION_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		Organization organization = organizationOptional.get();

		OrganizationDto organizationDto = new OrganizationDto();
		organizationDto.setId(organization.getId());
		organizationDto.setName(organization.getName());
		organizationDto.setEmail(organization.getEmail());
		organizationDto.setOwnerName(organization.getOwnerName());
		organizationDto.setContact(organization.getContact());

		return organizationDto;
	}

	@Override
	public List<OrganizationDto> getAllOrganizations() {

		List<Organization> organizations = organizationRepository.findAll();

		if (organizations.isEmpty()) {
			throw new OrganizationServiceException(ErrorConstant.ORGANIZATION_LIST_EMPTY, HttpStatus.NOT_FOUND);
		}

		List<OrganizationDto> organizationDtos = new ArrayList<>();

		for (Organization organization : organizations) {

			OrganizationDto organizationDto = new OrganizationDto();
			organizationDto.setId(organization.getId());
			organizationDto.setName(organization.getName());
			organizationDto.setEmail(organization.getEmail());
			organizationDto.setOwnerName(organization.getOwnerName());
			organizationDto.setContact(organization.getContact());

			organizationDtos.add(organizationDto);
		}

		return organizationDtos;
	}

	@Override
	public void deleteOrganizationById(int id) {

		if (id <= 0) {
			throw new OrganizationServiceException(ErrorConstant.ID_NOT_FOUND, HttpStatus.BAD_REQUEST);
		}
		Optional<Organization> optionalOrganization = organizationRepository.findById(id);

		if (optionalOrganization.isEmpty()) {
			throw new OrganizationServiceException(ErrorConstant.ORGANIZATION_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		Organization organization = optionalOrganization.get();

		organizationRepository.delete(organization);
	}

	@Override
	public void updateOrganizationById(int id, OrganizationDto organizationDto) {
		if (id <= 0) {
			throw new OrganizationServiceException(ErrorConstant.ID_NOT_FOUND, HttpStatus.BAD_REQUEST);
		}

		if (organizationDto == null) {
			throw new OrganizationServiceException(ErrorConstant.ORGANIZATION_DATA_REQUIRED, HttpStatus.BAD_REQUEST);
		}
		if (organizationDto.getName() == null) {
			throw new OrganizationServiceException(ErrorConstant.ORGANIZATION_NAME_REQUIRED, HttpStatus.BAD_REQUEST);
		}
		if (organizationDto.getEmail() == null) {
			throw new OrganizationServiceException(ErrorConstant.ORGANIZATION_EMAIL_REQUIRED, HttpStatus.BAD_REQUEST);
		}

		Optional<Organization> optionalOrganization = organizationRepository.findById(id);

		if (optionalOrganization.isEmpty()) {
			throw new OrganizationServiceException(ErrorConstant.ORGANIZATION_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		Organization existingOrganization = optionalOrganization.get();

		existingOrganization.setName(organizationDto.getName());
		existingOrganization.setEmail(organizationDto.getEmail());
		existingOrganization.setOwnerName(organizationDto.getOwnerName());
		existingOrganization.setContact(organizationDto.getContact());

		organizationRepository.save(existingOrganization);

	}

}
