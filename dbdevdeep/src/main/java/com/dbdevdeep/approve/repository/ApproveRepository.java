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
	
	Approve findByApproNo(Long approNo);
}
