package com.dbdevdeep.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbdevdeep.schedule.domain.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Long>{

	Holiday findByHolidayNo(Long holiday_no);
}
