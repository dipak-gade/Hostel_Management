package com.example.demo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.constant.ErrorConstant;
import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.entity.User;
import com.example.demo.exception.AuthServiceException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public LoginResponseDto login(LoginRequestDto requestDto) {

		User user = userRepository.findByUsername(requestDto.getUsername());

		if (user == null || !user.getPassword().equals(requestDto.getPassword())) {
			throw new AuthServiceException(ErrorConstant.INVALID_CREDENTIALS, HttpStatus.UNAUTHORIZED);
		}

		Integer orgId = user.getRole().equals("ADMIN") ? null : user.getOrgId();

		return new LoginResponseDto(user.getId(), user.getUsername(), user.getRole(), orgId);

	}
}
