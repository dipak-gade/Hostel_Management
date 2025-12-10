package com.example.demo.service;

import com.example.demo.dto.BreakupDto;

public interface BreakUpService {

	public BreakupDto getBreakup(String userName, Integer bedId, int duration);
}
