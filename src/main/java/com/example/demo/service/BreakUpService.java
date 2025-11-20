package com.example.demo.service;

import com.example.demo.dto.BreakupDTO;

public interface BreakUpService {

	public BreakupDTO getBreakup(String userName, Integer bedId, int duration);
}
