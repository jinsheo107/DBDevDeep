package com.dbdevdeep.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbdevdeep.student.domain.Student;

public interface StudentRepository extends JpaRepository<Student,Long>{
	
}