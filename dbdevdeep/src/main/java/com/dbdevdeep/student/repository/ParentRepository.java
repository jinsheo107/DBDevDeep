package com.dbdevdeep.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbdevdeep.student.domain.Parent;

public interface ParentRepository extends JpaRepository<Parent, Long>{
	List<Parent> findByStudent_StudentNo(Long studentNo);
}
