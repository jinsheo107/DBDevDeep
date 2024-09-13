package com.dbdevdeep.approve.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dbdevdeep.approve.domain.Approve;

public interface ApproveRepository extends JpaRepository<Approve, Long> {

	@Query("SELECT a FROM Approve a LEFT JOIN a.vacationRequests v WHERE a.approType = 0 AND a.employee.empId = :empId")
	List<Approve> findByTypeAndEmpId(@Param("empId") String empId);
	
	@Query(value = "SELECT a.appro_no AS approNo, a.appro_title AS approTitle, a.appro_time AS approTime, " +
            "a.appro_name AS approName, a.appro_status AS approStatus, " +
            "vr.vac_type AS vacType " +
            "FROM approve a " +
            "JOIN approve_line al ON a.appro_no = al.appro_no " +
            "LEFT JOIN vacation_request vr ON a.appro_no = vr.appro_no " +
            "WHERE al.emp_id = :loggedInUserEmpId " +
            "AND al.appro_line_status IN (1, 2, 3)", 
    nativeQuery = true)
List<Object[]> findApprovalRequestsForUser(@Param("loggedInUserEmpId") String loggedInUserEmpId);
	
	Approve findByApproNo(Long approNo);
}
