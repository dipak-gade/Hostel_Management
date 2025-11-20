package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.BedDTO;
import com.example.demo.entity.Bed;

public interface BedService {

	List<BedDTO> getAvailableBedsForTwoSharing(int sharing, int hostelId);

	public void addBedWithRoomId(BedDTO bedDTO);

	public List<Bed> getAllBeds();

}
