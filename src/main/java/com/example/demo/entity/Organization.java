package com.example.demo.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

//@Data // We dont need to add manually getters,setters,toStrings,equal,HahsCode methods
@Entity
public class Organization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int id;

	private String name;
	private String email;
	private String ownerName;
	private String contact;

	@OneToMany(mappedBy = "organization")
	@JsonIgnore
	private Set<Hostel> hostels;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
}
