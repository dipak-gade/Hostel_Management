package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.HostelDto;
import com.example.demo.entity.Hostel;

public interface HostelService {

//	public void addBuildingsInHostel(int hId, HostelDto hostelDto);

	public void addHostel(Hostel hostel);

	public void addHostelWithOrgId(HostelDto hostelDto);

	public Hostel getHostelById(int id);

	public List<Hostel> getAllHostels();

	List<Hostel> getHostelsByOrgId(int oId);

}
