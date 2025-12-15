package com.example.demo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.constant.ErrorConstant;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.UserServiceException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public void addUser(UserDto userDto) {

		if (userDto == null) {
			throw new UserServiceException(ErrorConstant.USER_DATA_REQUIRED, HttpStatus.BAD_REQUEST);
		}

		User user = new User();
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setCourse(userDto.getCourse());
		user.setAge(userDto.getAge());

		try {
			userRepository.save(user);
		} catch (Exception e) {
			throw new UserServiceException(ErrorConstant.SAVE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
