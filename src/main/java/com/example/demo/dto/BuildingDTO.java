package com.example.demo.dto;

public class BuildingDTO {

	private int hostelId;

	private String name;
	private int floors;
	private String warden;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFloors() {
		return floors;
	}

	public void setFloors(int floors) {
		this.floors = floors;
	}

	public String getWarden() {
		return warden;
	}

	public void setWarden(String warden) {
		this.warden = warden;
	}

	public int getHostelId() {
		return hostelId;
	}

	public void setHostelId(int hostelId) {
		this.hostelId = hostelId;
	}

//	private List<Floor> floors;
//
//	public List<Floor> getFloors() {
//		return floors;
//	}
//
//	public void setFloors(List<Floor> floors) {
//		this.floors = floors;
//	}

}
