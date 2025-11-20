//package com.example.demo.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.dto.BreakupDTO;
//import com.example.demo.service.BreakUpService;
//
//@RestController
//public class BreakUpController {
//
//	@Autowired
//	BreakUpService breakUpService;
//	
//	
//	@GetMapping("breakUp/{userName}/{bedId}/{duration}")
//	public BreakupDTO getBreakUp(String userName,Integer bedId,Integer duration) {
//		return breakUpService.getBreakup(userName, bedId, duration);
//	}
//	
//}
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BreakupDTO;
import com.example.demo.service.BreakUpService;

@RestController
public class BreakUpController {

	@Autowired
	BreakUpService breakUpService;

//	@GetMapping("breakUp/{userName}/{bedId}/{duration}")
//	public ResponseEntity<?> getBreakUp(@PathVariable String userName, @PathVariable Integer bedId, @PathVariable Integer duration) {
//
//		try {
//			BreakupDTO breakup = breakUpService.getBreakup(userName, bedId, duration);
//			return ResponseEntity.ok(breakup);
//		} catch (Exception e) {
//			// Log the error
//			System.err.println("Error in breakup API: " + e.getMessage());
//			e.printStackTrace();
//
//			// Return proper error response
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//					.body("Error generating breakup: " + e.getMessage());
//		}
//	}

	@GetMapping("breakUp/{userName}/{bedId}/{duration}")
	public BreakupDTO getBreakUp(@PathVariable String userName, @PathVariable Integer bedId,
			@PathVariable Integer duration) {

		return breakUpService.getBreakup(userName, bedId, duration);
	}
}