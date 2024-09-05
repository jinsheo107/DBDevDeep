package com.dbdevdeep.place.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dbdevdeep.place.domain.Place;

public interface PlaceRepository extends JpaRepository<Place, Long>{

	Place findByplaceNo(Long place_no);
	

    // unuseable_start_date에서 연도 추출
    @Query("SELECT SUBSTRING(p.unuseableStartDate, 1, 10) FROM Place p")
    List<String> findFormattedUnuseableStartDate();
    
 // unuseable_end_date에서 연도 추출
    @Query("SELECT SUBSTRING(p.unuseableEndDate, 1, 10) FROM Place p")
    List<String> findFormattedUnuseableEndDate();


}
	