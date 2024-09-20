package com.dbdevdeep.approve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbdevdeep.approve.domain.Approve;
import com.dbdevdeep.approve.domain.VacationRequest;

@Repository
public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long>{

	VacationRequest findByApprove(Approve approve);
	int deleteByApprove(Approve approve);
	
	List<VacationRequest> findByApprove_ApproStatusAndApprove_Employee_EmpId(int approveStatus, String empId);
	List<VacationRequest> findByApprove_ApproStatus(int approveStatus);
}
