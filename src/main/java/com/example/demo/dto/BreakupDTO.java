package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreakupDto {

	private String userName;
	private int duration;

	private String orgName;
	private String hostelName;
	private String hostelAdd;
	private int floorNo;

	private int roomNo;
	private int sharing;
	private String roomType;

	private int bedId;
	private int bedPrice;

	private int finalPrice;

}
