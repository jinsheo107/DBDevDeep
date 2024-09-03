package com.dbdevdeep.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbdevdeep.employee.domain.Department;

public interface DepartmentRepository extends JpaRepository<Department, String>{

	Department findByDeptCode(String dept_code);
}
