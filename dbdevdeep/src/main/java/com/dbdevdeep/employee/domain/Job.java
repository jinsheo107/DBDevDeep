package com.dbdevdeep.employee.domain;

import java.util.List;

import com.dbdevdeep.approve.domain.Approve;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="job")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class Job {
	
	@Id
	@Column(name="job_code")
	private String jobCode;
	
	@Column(name="job_name")
	private String jobName;
	
	@OneToMany(mappedBy = "job")
	private List<Employee> employees;
	
	@OneToMany(mappedBy = "job")
	private List<Approve> approves;
}
