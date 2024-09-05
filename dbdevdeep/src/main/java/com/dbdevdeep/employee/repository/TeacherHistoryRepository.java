package com.dbdevdeep.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbdevdeep.employee.domain.TeacherHistory;
import com.dbdevdeep.employee.vo.GradeClassGroup;

public interface TeacherHistoryRepository extends JpaRepository<TeacherHistory, Long>{
	
	@Query("SELECT COUNT(t) FROM TeacherHistory t WHERE t.tYear = :tYear")
	int findByTYearForCreateClass(@Param("tYear") String tYear);
	
	@Query("SELECT t FROM TeacherHistory t WHERE t.tYear = :tYear")
	List<TeacherHistory> findByClassByYear(@Param("tYear") String tYear);
	
}
