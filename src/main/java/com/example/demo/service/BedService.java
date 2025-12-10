package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.BedDto;
import com.example.demo.entity.Bed;

public interface BedService {

	List<BedDto> getAvailableBedsForTwoSharing(int sharing, int hostelId);

	public void addBedWithRoomId(BedDto bedDto);

	public List<Bed> getAllBeds();

}
