package com.dbdevdeep.approve.domain;

import com.dbdevdeep.employee.domain.Employee;

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
public class ReferenceDto {

	private Long ref_no;
	private Long appro_no;
	private String emp_id;
	private String ref_name;
	
	public Reference toEntity(Approve approve , Employee employee) {
		return Reference.builder()
				.refNo(ref_no)
				.approve(approve)
				.employee(employee)
				.refName(ref_name)
				.build();
	}
	
	public ReferenceDto toDto(Reference reference) {
		return ReferenceDto.builder()
				.ref_no(reference.getRefNo())
				.appro_no(reference.getApprove().getApproNo())
				.emp_id(reference.getEmployee().getEmpId())
				.ref_name(reference.getRefName())
				.build();
	}
}
