
package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BreakupDTO;
import com.example.demo.entity.Bed;
import com.example.demo.repository.BedRepo;

import java.util.Optional;

@Service
public class BreakUpServiceImpl implements BreakUpService {

    @Autowired
    BedRepo bedRepo;

    @Override
    public BreakupDTO getBreakup(String userName, Integer bedId, int duration) {
        // Check if bed exists
        Optional<Bed> bedOptional = bedRepo.findById(bedId);
        if (!bedOptional.isPresent()) {
            throw new RuntimeException("Bed not found with ID: " + bedId);
        }
        
        Bed bed = bedOptional.get();

        BreakupDTO breakupDTO = new BreakupDTO();
        breakupDTO.setUserName(userName);
        breakupDTO.setDuration(duration);
        breakupDTO.setBedId(bed.getId());
        breakupDTO.setBedPrice(bed.getPrice());

        // Add null checks for all relationships
        if (bed.getRoom() == null) {
            throw new RuntimeException("Room not found for bed ID: " + bedId);
        }
        
        breakupDTO.setRoomNo(bed.getRoom().getRoomNo());
        breakupDTO.setSharing(bed.getRoom().getSharing());
        breakupDTO.setRoomType(bed.getRoom().getType());

        if (bed.getRoom().getFloor() == null) {
            throw new RuntimeException("Floor not found for room");
        }
        
        breakupDTO.setFloorNo(bed.getRoom().getFloor().getFloorNo());

        if (bed.getRoom().getFloor().getBuilding() == null) {
            throw new RuntimeException("Building not found for floor");
        }

        if (bed.getRoom().getFloor().getBuilding().getHostel() == null) {
            throw new RuntimeException("Hostel not found for building");
        }
        
        breakupDTO.setHostelName(bed.getRoom().getFloor().getBuilding().getHostel().getName());
        breakupDTO.setHostelAdd(bed.getRoom().getFloor().getBuilding().getHostel().getAddress());

        if (bed.getRoom().getFloor().getBuilding().getHostel().getOrganization() == null) {
            throw new RuntimeException("Organization not found for hostel");
        }
        
        breakupDTO.setOrgName(bed.getRoom().getFloor().getBuilding().getHostel().getOrganization().getName());

        int finalPrice = bed.getPrice() * duration;
        breakupDTO.setFinalPrice(finalPrice);

        return breakupDTO;
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
