package com.dbdevdeep.approve.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dbdevdeep.approve.domain.Approve;
import com.dbdevdeep.approve.domain.ApproveLine;

@Repository
public interface ApproveLineRepository extends JpaRepository<ApproveLine, Long>{
	List<ApproveLine> findByApprove(Approve approve);

	int deleteByApprove(Approve approve);
	
	// 반려 정보를 조회하는 메서드
    @Query("SELECT al FROM ApproveLine al WHERE al.approve.approNo = :approNo AND al.approLineStatus = 3")
    ApproveLine backReasonByApproveNo(@Param("approNo") Long approNo);
}
