package com.dbdevdeep.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbdevdeep.student.domain.StudentClass;

public interface StudentClassRepository extends JpaRepository<StudentClass, Long>{

}
