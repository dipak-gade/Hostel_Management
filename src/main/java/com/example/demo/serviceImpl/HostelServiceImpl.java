package com.example.demo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.HostelDto;
import com.example.demo.entity.Hostel;
import com.example.demo.entity.Organization;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.HostelRepository;
import com.example.demo.repository.OrganizationRepository;
import com.example.demo.service.HostelService;

@Service
public class HostelServiceImpl implements HostelService {

	@Autowired
	HostelRepository hostelRepository;

	@Autowired
	BuildingRepository buildingRepository;

	@Autowired
	OrganizationRepository organizationRepository;

	@Override
	public List<Hostel> getAllHostels() {
		return hostelRepository.findAll();
	}

	@Override
	public Hostel getHostelById(int id) {
		return hostelRepository.findById(id).get();

	}

	@Override
	public void addHostel(Hostel hostel) {
		hostelRepository.save(hostel);
	}

	@Override
	public void addHostelWithOrgId(HostelDto hostelDto) {
		Organization organization = organizationRepository.findById(hostelDto.getOrgId()).get();

		Hostel hostel = new Hostel();
		hostel.setName(hostelDto.getName());
		hostel.setAddress(hostelDto.getAddress());
		hostel.setCapacity(hostelDto.getCapacity());
		hostel.setType(hostelDto.getType());
		hostel.setImage(hostelDto.getImage());

		hostel.setOrganization(organization);
		hostelRepository.save(hostel);

	}

	@Override
	public List<Hostel> getHostelsByOrgId(int oId) {
		return hostelRepository.getHostelsByOrgId(oId);

	}
}
