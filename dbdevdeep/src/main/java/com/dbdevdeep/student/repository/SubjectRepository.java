package com.dbdevdeep.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbdevdeep.student.domain.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{
	List<Subject> findBysubjectTeacher(String emp_name);
	
	Subject findBysubjectNo(Long subject_no);
}
