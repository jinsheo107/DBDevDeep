package com.dbdevdeep.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbdevdeep.student.domain.Student;

public interface StudentRepository extends JpaRepository<Student,Long>{
	// 학생 상세 조회를 위한 학생번호를 통해 한사람의 정보를 가져오는 실행문
	Student findBystudentNo(Long student_no);
	
}