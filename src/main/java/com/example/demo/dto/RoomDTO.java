package com.example.demo.dto;

import java.util.Set;

import com.example.demo.entity.Bed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {

	private int floorId;

	private int roomNo;
	private int sharing;
	private String type;

	Set<Bed> beds;

}
