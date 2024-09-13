package com.dbdevdeep.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dbdevdeep.student.domain.StudentClass;

public interface StudentClassRepository extends JpaRepository<StudentClass, Long>{
	List<StudentClass> findByStudent_StudentNo(Long studentNo);
	
	@Query("SELECT sc FROM StudentClass sc " +
		       "WHERE sc.teacherHistory.grade = (SELECT MAX(th.grade) FROM StudentClass sc2 JOIN sc2.teacherHistory th WHERE sc2.student.studentNo = sc.student.studentNo)")
	List<StudentClass> findRecentYearAll();
}
