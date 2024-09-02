package com.dbdevdeep.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbdevdeep.employee.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String>{
	Employee findByempId(String emp_id);
	
	Employee findBygovId(String gov_id);
	
	@Query("SELECT e.empId FROM Employee e WHERE e.empId LIKE CONCAT(:year, '%') ORDER BY e.empId DESC LIMIT 1")
	String findByempIdWhen(@Param("year") String year);

}
