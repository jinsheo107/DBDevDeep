package com.dbdevdeep.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbdevdeep.employee.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String>{
	Employee findByempId(String emp_id);
	
	Employee findBygovId(String gov_id);
	
	@Query("SELECT COUNT(e) FROM Employee e WHERE e.empId LIKE CONCAT(:year, '%')")
	int findByempIdWhen(@Param("year") String year);

}
