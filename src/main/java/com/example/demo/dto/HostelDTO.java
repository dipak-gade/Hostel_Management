package com.example.demo.dto;

public class HostelDTO {

	private int orgId;

	private String name;
	private String address;
	private int capacity;
	private String type;
	private String image;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

//	private Set<Building> buildings;
//
//	public Set<Building> getBuildings() {
//		return buildings;
//	}
//
//	public void setBuildings(Set<Building> buildings) {
//		this.buildings = buildings;
//	}
}
