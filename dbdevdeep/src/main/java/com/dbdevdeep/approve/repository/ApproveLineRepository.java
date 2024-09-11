package com.dbdevdeep.approve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbdevdeep.approve.domain.Approve;
import com.dbdevdeep.approve.domain.ApproveLine;

@Repository
public interface ApproveLineRepository extends JpaRepository<ApproveLine, Long>{
	List<ApproveLine> findByApprove(Approve approve);
}
