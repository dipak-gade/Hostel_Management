package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.HostelDto;

public interface HostelService {

	public void addHostelWithOrgId(HostelDto hostelDto);

	public HostelDto getHostelById(int id);

	public List<HostelDto> getAllHostels();

	List<HostelDto> getHostelsByOrgId(int oId);

}
