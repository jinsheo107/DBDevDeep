package com.dbdevdeep.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbdevdeep.employee.domain.Job;

public interface JobRepository extends JpaRepository<Job, String>{

	Job findByJobCode(String job_code);

}
