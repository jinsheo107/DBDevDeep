package com.dbdevdeep.notice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbdevdeep.notice.domain.NoticeComment;

public interface NoticeCommentRepository extends JpaRepository<NoticeComment,Long> {
	
	@Query("SELECT nc FROM NoticeComment nc WHERE nc.notice.noticeNo = :noticeNo")
    List<NoticeComment> findByNoticeNo(@Param("noticeNo") Long noticeNo);
}
