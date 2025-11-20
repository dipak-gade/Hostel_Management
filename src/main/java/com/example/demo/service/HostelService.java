package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.HostelDTO;
import com.example.demo.entity.Hostel;

public interface HostelService {

//	public void addBuildingsInHostel(int hId, HostelDTO hostelDTO);

	public void addHostel(Hostel hostel);

	public void addHostelWithOrgId(HostelDTO hostelDTO);

	public Hostel getHostelById(int id);

	public List<Hostel> getAllHostels();

	List<Hostel> getHostelsByOrgId(int oId);

}
