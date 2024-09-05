package com.dbdevdeep.place.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbdevdeep.place.domain.Place;

public interface PlaceRepository extends JpaRepository<Place, Long>{

	Place findByplaceNo(Long place_no);
	
}
