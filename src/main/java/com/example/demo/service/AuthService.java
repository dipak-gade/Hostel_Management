package com.example.demo.service;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.LoginResponseDto;

public interface AuthService {
	public LoginResponseDto login(LoginRequestDto requestDto);
	
}
