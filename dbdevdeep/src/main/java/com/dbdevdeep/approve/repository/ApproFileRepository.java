package com.dbdevdeep.approve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbdevdeep.approve.domain.ApproFile;
import com.dbdevdeep.approve.domain.Approve;

@Repository
public interface ApproFileRepository extends JpaRepository<ApproFile, Long>{
	
	ApproFile findByApprove(Approve approve);
	
	List<ApproFile> findAllByApprove(Approve approve);
}
