
package com.example.demo.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BreakupDto;
import com.example.demo.entity.Bed;
import com.example.demo.repository.BedRepository;
import com.example.demo.service.BreakUpService;

@Service
public class BreakUpServiceImpl implements BreakUpService {

    @Autowired
    BedRepository bedRepository;

    @Override
    public BreakupDto getBreakup(String userName, Integer bedId, int duration) {
        // Check if bed exists
        Optional<Bed> bedOptional = bedRepository.findById(bedId);
        if (!bedOptional.isPresent()) {
            throw new RuntimeException("Bed not found with ID: " + bedId);
        }
        
        Bed bed = bedOptional.get();

        BreakupDto breakupDto = new BreakupDto();
        breakupDto.setUserName(userName);
        breakupDto.setDuration(duration);
        breakupDto.setBedId(bed.getId());
        breakupDto.setBedPrice(bed.getPrice());

        // Add null checks for all relationships
        if (bed.getRoom() == null) {
            throw new RuntimeException("Room not found for bed ID: " + bedId);
        }
        
        breakupDto.setRoomNo(bed.getRoom().getRoomNo());
        breakupDto.setSharing(bed.getRoom().getSharing());
        breakupDto.setRoomType(bed.getRoom().getType());

        if (bed.getRoom().getFloor() == null) {
            throw new RuntimeException("Floor not found for room");
        }
        
        breakupDto.setFloorNo(bed.getRoom().getFloor().getFloorNo());

        if (bed.getRoom().getFloor().getBuilding() == null) {
            throw new RuntimeException("Building not found for floor");
        }

        if (bed.getRoom().getFloor().getBuilding().getHostel() == null) {
            throw new RuntimeException("Hostel not found for building");
        }
        
        breakupDto.setHostelName(bed.getRoom().getFloor().getBuilding().getHostel().getName());
        breakupDto.setHostelAdd(bed.getRoom().getFloor().getBuilding().getHostel().getAddress());

        if (bed.getRoom().getFloor().getBuilding().getHostel().getOrganization() == null) {
            throw new RuntimeException("Organization not found for hostel");
        }
        
        breakupDto.setOrgName(bed.getRoom().getFloor().getBuilding().getHostel().getOrganization().getName());

        int finalPrice = bed.getPrice() * duration;
        breakupDto.setFinalPrice(finalPrice);

        return breakupDto;
    }
}
//package com.example.demo.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.dto.BreakupDTO;
//import com.example.demo.entity.Bed;
//import com.example.demo.repository.BedRepo;
//
//@Service
//public class BreakUpServiceImpl implements BreakUpService {
//
//	@Autowired
//	BedRepo bedRepo;
//
//	@Override
//	public BreakupDTO getBreakup(String userName, Integer bedId, int duration) {
//		Bed bed = bedRepo.findById(bedId).get();
//
//		BreakupDTO breakupDTO = new BreakupDTO();
//		breakupDTO.setUserName(userName);
//		breakupDTO.setDuration(duration);
//
//		breakupDTO.setBedId(bed.getId());
//		breakupDTO.setBedPrice(bed.getPrice());
//
//		breakupDTO.setHostelName(bed.getRoom().getFloor().getBuilding().getHostel().getName());
//		breakupDTO.setHostelAdd(bed.getRoom().getFloor().getBuilding().getHostel().getAddress());
//		breakupDTO.setRoomType(bed.getRoom().getType());
//		breakupDTO.setOrgName(bed.getRoom().getFloor().getBuilding().getHostel().getOrganization().getName());
//		breakupDTO.setFloorNo(bed.getRoom().getFloor().getFloorNo());
//		breakupDTO.setRoomNo(bed.getRoom().getRoomNo());
//		breakupDTO.setSharing(bed.getRoom().getSharing());
//
//		int finalPrice = bed.getPrice() * duration;
//
//		breakupDTO.setFinalPrice(finalPrice);
//
//		return breakupDTO;
//	}
//
//}
