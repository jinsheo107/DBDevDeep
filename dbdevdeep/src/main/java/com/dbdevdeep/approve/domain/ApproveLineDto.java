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
public class ApproveLineDto {

	private Long appro_line_no;
	private Long appro_no;
	private String emp_id;
	private int appro_line_order;
	private int appro_line_status;
	private LocalDateTime appro_line_date;
	private String appro_back;
	private String consult_yn;
	
	public ApproveLine toEntity() {
		return ApproveLine.builder()
				.approLineNo(appro_line_no)
				.empId(emp_id)
				.approLineOrder(appro_line_order)
				.approLineStatus(appro_line_status)
				.approLineDate(appro_line_date)
				.approBack(appro_back)
				.consultYn(consult_yn)
				.build();
	}
	
	public ApproveLineDto toDto(ApproveLine approveLine) {
		return ApproveLineDto.builder()
				.appro_line_no(approveLine.getApproLineNo())
				.emp_id(approveLine.getEmpId())
				.appro_line_order(approveLine.getApproLineOrder())
				.appro_line_status(approveLine.getApproLineStatus())
				.appro_line_date(approveLine.getApproLineDate())
				.appro_back(approveLine.getApproBack())
				.consult_yn(approveLine.getConsultYn())
				.build();
				
	}
	
}
