package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Bed;
import com.example.demo.entity.User;
import com.example.demo.exception.InvalidDataException;
import com.example.demo.repository.BedRepo;
import com.example.demo.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	BedRepo bedRepo;

//	@Override
//	public void addUserWithBedId(int bedId, UserDTO userDTO) {
//
//		Bed bed = bedRepo.findById(bedId).get();
//		if (bed.getStatus().equals("Available")) {
//
//			User user = new User();
//			user.setName(userDTO.getName());
//			user.setEmail(userDTO.getEmail());
//			user.setCourse(userDTO.getCourse());
//			user.setAge(userDTO.getAge());
//
//			user.setBed(bed);
//
//			userRepo.save(user);
//			bed.setStatus("Occupied");
//
//			bedRepo.save(bed);
//		} else {
//			throw new InvalidDataException("Bed is already occupied");
//		}
//
//	}

	@Override
	public void addUser(User user) {
		userRepo.save(user);
	}

}
