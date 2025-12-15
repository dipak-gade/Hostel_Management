package com.example.demo.dto;

import java.util.List;

import com.example.demo.entity.Bed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {

	private int floorId;

	private int id;
	private int roomNo;
	private int sharing;
	private String type;

	List<Bed> beds;

}
