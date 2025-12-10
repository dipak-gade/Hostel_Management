package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostelDto {

	private int orgId;

	private String name;
	private String address;
	private int capacity;
	private String type;
	private String image;

}
