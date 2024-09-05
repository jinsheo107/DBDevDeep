package com.dbdevdeep.notice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbdevdeep.notice.domain.NoticeReadCheck;

public interface NoticeReadCheckRepository extends JpaRepository<NoticeReadCheck, Long>{
	
	List<NoticeReadCheck> findByEmployee_EmpId(String read_id);
}
