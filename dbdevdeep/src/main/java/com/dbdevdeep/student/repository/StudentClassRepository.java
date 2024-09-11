package com.dbdevdeep.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbdevdeep.student.domain.StudentClass;

public interface StudentClassRepository extends JpaRepository<StudentClass, Long>{
	List<StudentClass> findByStudent_StudentNo(Long studentNo);
}
