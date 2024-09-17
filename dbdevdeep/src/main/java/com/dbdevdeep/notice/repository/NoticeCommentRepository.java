package com.dbdevdeep.notice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dbdevdeep.notice.domain.NoticeComment;

@Repository
public interface NoticeCommentRepository extends JpaRepository<NoticeComment,Long> {
	
//	// 게시글에 해당하는 댓글 리스트 조회
//	@Query("SELECT nc FROM NoticeComment nc WHERE nc.notice.noticeNo = :noticeNo")
//    List<NoticeComment> findByNoticeNo(@Param("noticeNo") Long noticeNo);
	
	// 부모 댓글만 조회 (parent_cmt_no가 null인 댓글)
    List<NoticeComment> findByNoticeNoticeNoAndParentCommentIsNull(Long noticeNo);
    
    // 특정 부모 댓글의 자식 댓글 조회
    List<NoticeComment> findByParentCommentCmtNo(Long parentCommentNo);
	
	
	// 댓글 번호로 단일 댓글 조회
	NoticeComment findBycmtNo(Long cmt_no);
	
	// 댓글 삭제(소프트 삭제)
	@Modifying
	@Query("UPDATE NoticeComment nc SET nc.isDelete = :isDelete WHERE nc.cmtNo = :cmtNo")
	int deleteCmtId(@Param("cmtNo") Long cmtNo, @Param("isDelete") int isDelete);
}
