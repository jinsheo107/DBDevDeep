package com.dbdevdeep.employee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DepartmentDto {

	private String dept_code;
	private String dept_title;
	
	public Department toEntity() {
		return Department.builder()
				.deptCode(dept_code).deptTitle(dept_title).build();
	}
	
	public DepartmentDto toDto(Department dept) {
		return DepartmentDto.builder()
				.dept_code(dept.getDeptCode()).dept_title(dept.getDeptTitle()).build();
	}
}
