package com.dbdevdeep.approve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbdevdeep.approve.domain.Approve;
import com.dbdevdeep.approve.domain.VacationRequest;

@Repository
public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long>{

	VacationRequest findByApprove(Approve approve);
}
