package com.example.demo.dto;

import java.util.Set;

import com.example.demo.entity.Hostel;

public class OrganizationDTO {

	private String name;
	private String email;
	private String ownerName;

	private Set<Hostel> hostels;

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Hostel> getHostels() {
		return hostels;
	}

	public void setHostels(Set<Hostel> hostels) {
		this.hostels = hostels;
	}

}
