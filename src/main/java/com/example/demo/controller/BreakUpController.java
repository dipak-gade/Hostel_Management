
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BreakupDto;
import com.example.demo.service.BreakUpService;

@RestController
public class BreakUpController {

	@Autowired
	BreakUpService breakUpService;



	@GetMapping("breakUp/{userName}/{bedId}/{duration}")
	public BreakupDto getBreakUp(@PathVariable String userName, @PathVariable Integer bedId,
			@PathVariable Integer duration) {

		return breakUpService.getBreakup(userName, bedId, duration);
	}
}