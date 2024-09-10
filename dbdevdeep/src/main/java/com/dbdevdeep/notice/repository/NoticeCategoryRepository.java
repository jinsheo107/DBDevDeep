package com.dbdevdeep.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbdevdeep.notice.domain.NoticeCategory;

public interface NoticeCategoryRepository extends JpaRepository<NoticeCategory, Long>{
	NoticeCategory findBycategoryNo(Long category_no);
}
