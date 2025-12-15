package com.example.demo.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.constant.ErrorConstant;
import com.example.demo.dto.HostelDto;
import com.example.demo.entity.Hostel;
import com.example.demo.entity.Organization;
import com.example.demo.exception.HostelServiceException;
import com.example.demo.repository.HostelRepository;
import com.example.demo.repository.OrganizationRepository;
import com.example.demo.service.HostelService;

@Service
public class HostelServiceImpl implements HostelService {

	@Autowired
	HostelRepository hostelRepository;

	@Autowired
	OrganizationRepository organizationRepository;

	@Override
	public void addHostelWithOrgId(HostelDto hostelDto) {

		if (hostelDto == null) {
			throw new HostelServiceException(ErrorConstant.HOSTEL_DATA_REQUIRED, HttpStatus.BAD_REQUEST);
		}

		if (hostelDto.getOrgId() <= 0) {
			throw new HostelServiceException(ErrorConstant.INVALID_ID, HttpStatus.BAD_REQUEST);
		}

		Optional<Organization> organizationOptional = organizationRepository.findById(hostelDto.getOrgId());

		if (organizationOptional.isEmpty()) {
			throw new HostelServiceException(ErrorConstant.ORGANIZATION_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		Organization organization = organizationOptional.get();

		Hostel hostel = new Hostel();
		hostel.setName(hostelDto.getName());
		hostel.setAddress(hostelDto.getAddress());
		hostel.setCapacity(hostelDto.getCapacity());
		hostel.setType(hostelDto.getType());
		hostel.setImage(hostelDto.getImage());

		hostel.setOrganization(organization);

		try {
			hostelRepository.save(hostel);
		} catch (Exception e) {
			throw new HostelServiceException(ErrorConstant.SAVE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public HostelDto getHostelById(int id) {
		if (id <= 0) {
			throw new HostelServiceException(ErrorConstant.INVALID_ID, HttpStatus.BAD_REQUEST);
		}
		Optional<Hostel> hostelOptional = hostelRepository.findById(id);
		if (hostelOptional.isEmpty()) {
			throw new HostelServiceException(ErrorConstant.HOSTEL_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		Hostel hostel = hostelOptional.get();
		HostelDto hostelDto = new HostelDto();

		hostelDto.setId(hostel.getId());
		hostelDto.setOrgId(hostel.getOrganization().getId());
		hostelDto.setName(hostel.getName());
		hostelDto.setAddress(hostel.getAddress());
		hostelDto.setCapacity(hostel.getCapacity());
		hostelDto.setType(hostel.getType());
		hostelDto.setImage(hostel.getImage());

		return hostelDto;

	}

	@Override
	public List<HostelDto> getAllHostels() {

		List<Hostel> hostels = hostelRepository.findAll();

		if (hostels.isEmpty()) {
			throw new HostelServiceException(ErrorConstant.HOSTEL_LIST_EMPTY, HttpStatus.NOT_FOUND);
		}

		List<HostelDto> hostelDtos = new ArrayList<>();

		for (Hostel hostel : hostels) {
			HostelDto hostelDto = new HostelDto();

			hostelDto.setId(hostel.getId());
			hostelDto.setOrgId(hostel.getOrganization().getId());
			hostelDto.setName(hostel.getName());
			hostelDto.setAddress(hostel.getAddress());
			hostelDto.setCapacity(hostel.getCapacity());
			hostelDto.setType(hostel.getType());
			hostelDto.setImage(hostel.getImage());

			hostelDtos.add(hostelDto);
		}

		return hostelDtos;
	}

	@Override
	public List<HostelDto> getHostelsByOrgId(int orgId) {

		if (orgId <= 0) {
			throw new HostelServiceException(ErrorConstant.INVALID_ID, HttpStatus.BAD_REQUEST);
		}

		List<Hostel> hostels = hostelRepository.getHostelsByOrgId(orgId);

		if (hostels.isEmpty()) {
			throw new HostelServiceException(ErrorConstant.HOSTEL_LIST_EMPTY, HttpStatus.NOT_FOUND);
		}

		List<HostelDto> hostelDtos = new ArrayList<>();

		for (Hostel hostel : hostels) {

			HostelDto hostelDto = new HostelDto();

			hostelDto.setId(hostel.getId());
			hostelDto.setOrgId(hostel.getOrganization().getId());
			hostelDto.setName(hostel.getName());
			hostelDto.setAddress(hostel.getAddress());
			hostelDto.setCapacity(hostel.getCapacity());
			hostelDto.setType(hostel.getType());
			hostelDto.setImage(hostel.getImage());

			hostelDtos.add(hostelDto);

		}

		return hostelDtos;

	}
}
