
package com.example.demo.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.constant.ErrorConstant;
import com.example.demo.dto.BreakupDto;
import com.example.demo.entity.Bed;
import com.example.demo.exception.BreakupServiceException;
import com.example.demo.repository.BedRepository;
import com.example.demo.service.BreakUpService;

@Service
public class BreakUpServiceImpl implements BreakUpService {

	@Autowired
	BedRepository bedRepository;

	@Override
	public BreakupDto getBreakup(String userName, Integer bedId, int duration) {

		if (bedId == null || bedId <= 0) {
			throw new BreakupServiceException(ErrorConstant.INVALID_ID, HttpStatus.BAD_REQUEST);
		}

		if (duration <= 0) {
			throw new BreakupServiceException(ErrorConstant.INVALID_ID, HttpStatus.BAD_REQUEST);
		}

		Optional<Bed> bedOptional = bedRepository.findById(bedId);

		if (bedOptional.isEmpty()) {
			throw new BreakupServiceException(ErrorConstant.BED_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		Bed bed = bedOptional.get();

		if (bed.getRoom() == null) {
			throw new BreakupServiceException(ErrorConstant.ROOM_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		if (bed.getRoom().getFloor() == null) {
			throw new BreakupServiceException(ErrorConstant.FLOOR_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		if (bed.getRoom().getFloor().getBuilding() == null) {
			throw new BreakupServiceException(ErrorConstant.BUILDING_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		if (bed.getRoom().getFloor().getBuilding().getHostel() == null) {
			throw new BreakupServiceException(ErrorConstant.HOSTEL_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		if (bed.getRoom().getFloor().getBuilding().getHostel().getOrganization() == null) {
			throw new BreakupServiceException(ErrorConstant.ORGANIZATION_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		BreakupDto breakupDto = new BreakupDto();
		breakupDto.setUserName(userName);
		breakupDto.setDuration(duration);
		breakupDto.setBedId(bed.getId());
		breakupDto.setBedPrice(bed.getPrice());

		breakupDto.setRoomNo(bed.getRoom().getRoomNo());
		breakupDto.setSharing(bed.getRoom().getSharing());
		breakupDto.setRoomType(bed.getRoom().getType());
		breakupDto.setFloorNo(bed.getRoom().getFloor().getFloorNo());

		breakupDto.setHostelName(bed.getRoom().getFloor().getBuilding().getHostel().getName());
		breakupDto.setHostelAdd(bed.getRoom().getFloor().getBuilding().getHostel().getAddress());

		breakupDto.setOrgName(bed.getRoom().getFloor().getBuilding().getHostel().getOrganization().getName());

		breakupDto.setFinalPrice(bed.getPrice() * duration);

		return breakupDto;
	}
}