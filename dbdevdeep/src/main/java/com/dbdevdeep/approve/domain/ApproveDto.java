package com.dbdevdeep.approve.domain;

import java.time.LocalDateTime;


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
public class ApproveDto {

	private Long appro_no;
	private String emp_id;
	private int temp_no;
	private LocalDateTime appro_date;
	private int appro_type;
	private int appro_status;
	private String appro_name;
	private String appro_context;
	
	
	public Approve toEntity() {
		return Approve.builder()
				.approNo(appro_no)
				.empId(emp_id)
				.tempNo(temp_no)
				.approDate(appro_date)
				.approType(appro_type)
				.approStatus(appro_status)
				.approName(appro_name)
				.approContext(appro_context)
				.build();
				
	}
	
	public ApproveDto toDto(Approve approve) {
		return ApproveDto.builder()
				.appro_no(approve.getApproNo())
				.emp_id(approve.getEmpId())
				.temp_no(approve.getTempNo())
				.appro_date(approve.getApproDate())
				.appro_type(approve.getApproType())
				.appro_status(approve.getApproStatus())
				.appro_name(approve.getApproName())
				.appro_context(approve.getApproContext())
				.build();
				
	}
}
