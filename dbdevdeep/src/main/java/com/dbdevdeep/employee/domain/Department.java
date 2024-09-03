package com.dbdevdeep.employee.domain;

import java.util.List;

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
@Table(name="department")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class Department {

	@Id
	@Column(name="dept_code")
	private String deptCode;
	
	@Column(name="dept_title")
	private String deptTitle;
	
	@OneToMany(mappedBy = "department")
	private List<Employee> employees;
}
