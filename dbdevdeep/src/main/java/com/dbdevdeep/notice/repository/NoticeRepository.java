package com.dbdevdeep.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbdevdeep.notice.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
	
}
