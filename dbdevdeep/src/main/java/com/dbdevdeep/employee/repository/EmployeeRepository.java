package com.dbdevdeep.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbdevdeep.employee.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String>{
	Employee findByempId(String emp_id);
	
	Employee findBygovId(String gov_id);

}
