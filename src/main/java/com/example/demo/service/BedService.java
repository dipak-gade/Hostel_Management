package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.BedDto;

public interface BedService {

	public void addBedWithRoomId(BedDto bedDto);

	public List<BedDto> getAllBeds();

	List<BedDto> getBedsByRoomId(int roomId);

	List<BedDto> getAvailableBedsForRoomSharing(int sharing, int hostelId);

}
