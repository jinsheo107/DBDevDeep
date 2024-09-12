package com.dbdevdeep.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbdevdeep.schedule.domain.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>{

	List<Schedule> findByCalendarType(int calendarType);

	List<Schedule> findByCalendarTypeAndEmployee_EmpId(int calendarType, String empId);

}
