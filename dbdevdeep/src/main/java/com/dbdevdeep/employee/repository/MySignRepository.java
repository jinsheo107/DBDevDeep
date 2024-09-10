package com.dbdevdeep.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbdevdeep.employee.domain.MySign;

public interface MySignRepository extends JpaRepository <MySign, Long> {

	@Query("SELECT ms FROM MySign ms WHERE ms.employee.empId = :emp_id")
	List<MySign> mySignfindAllByEmpid(@Param("emp_id") String emp_id);
}
