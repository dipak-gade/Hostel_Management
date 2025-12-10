package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Hostel;

public interface HostelRepository extends JpaRepository<Hostel, Integer> {

	@Query("select h from Hostel h where h.organization.id=:oId")
	List<Hostel> getHostelsByOrgId(@Param("oId") int id);

}
