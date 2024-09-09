package com.dbdevdeep.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbdevdeep.notice.domain.NoticeReadCheck;

public interface NoticeCategoryRepository extends JpaRepository<NoticeReadCheck, Long>{

}
