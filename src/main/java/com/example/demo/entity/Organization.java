package com.example.demo.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data // We dont need to add manually getters,setters,toStrings,equal,HahsCode methods
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

}
