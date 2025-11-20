package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.HostelDTO;
import com.example.demo.entity.Hostel;
import com.example.demo.entity.Organization;
import com.example.demo.repository.BuildingRepo;
import com.example.demo.repository.HostelRepo;
import com.example.demo.repository.OrganizationRepo;

@Service
public class HostelServiceImpl implements HostelService {

	@Autowired
	HostelRepo hostelRepo;

	@Autowired
	BuildingRepo buildingRepo;

	@Autowired
	OrganizationRepo organizationRepo;

//	@Override
//	public void addBuildingsInHostel(int hId, HostelDTO hostelDTO) {
//		Hostel hostel = hostelRepo.findById(hId).get();
//
//		Set<Building> buildings = hostelDTO.getBuildings();
//
//		for (Building b : buildings) {
//			b.setHostel(hostel);
//			buildingRepo.save(b);
//		}
//
//	}

	@Override
	public List<Hostel> getAllHostels() {
		return hostelRepo.findAll();
	}

	@Override
	public Hostel getHostelById(int id) {
		return hostelRepo.findById(id).get();

	}

	@Override
	public void addHostel(Hostel hostel) {
		hostelRepo.save(hostel);
	}

	@Override
	public void addHostelWithOrgId(HostelDTO hostelDTO) {
		Organization organization = organizationRepo.findById(hostelDTO.getOrgId()).get();

		Hostel hostel = new Hostel();
		hostel.setName(hostelDTO.getName());
		hostel.setAddress(hostelDTO.getAddress());
		hostel.setCapacity(hostelDTO.getCapacity());
		hostel.setType(hostelDTO.getType());
		hostel.setImage(hostelDTO.getImage());

		hostel.setOrganization(organization);
		hostelRepo.save(hostel);

	}

	@Override
	public List<Hostel> getHostelsByOrgId(int oId) {
		return hostelRepo.getHostelsByOrgId(oId);

	}
}
