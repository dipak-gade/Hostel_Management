package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BedDto {

	private Integer bedId;
	private int roomId;

	private int bedNo;
	private String status;
	private int price;

	private int roomNo;
	private String buildingName;
	private int floorNo;
	private String hostelName;
	private String address;

}
