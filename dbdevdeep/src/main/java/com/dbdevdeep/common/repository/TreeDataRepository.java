package com.dbdevdeep.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbdevdeep.employee.domain.Employee;

public interface TreeDataRepository extends JpaRepository<Employee, String>{

	List<Employee> findAll();
}
