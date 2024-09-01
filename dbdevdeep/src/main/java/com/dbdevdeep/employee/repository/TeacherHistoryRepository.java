package com.dbdevdeep.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbdevdeep.employee.domain.TeacherHistory;

public interface TeacherHistoryRepository extends JpaRepository<TeacherHistory, Long>{
	
}
