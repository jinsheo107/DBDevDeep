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
public class VacationRequestDto {

	private Long vac_no;
	private Long appro_no;
	private String vac_yn;
	private int vac_type;
	private LocalDateTime start_time;
	private LocalDateTime end_time;
	
	public VacationRequest toEntity(Approve approve) {
		return VacationRequest.builder()
				.vacNo(vac_no)
				.approve(approve)
				.vacYn(vac_yn)
				.vacType(vac_type)
				.startTime(start_time)
				.endTime(end_time)
				.build();
	}
	
	public VacationRequestDto toDto(VacationRequest vacationRequest) {
		return VacationRequestDto.builder()
				.vac_no(vacationRequest.getVacNo())
				.appro_no(vacationRequest.getApprove().getApproNo())
				.vac_yn(vacationRequest.getVacYn())
				.vac_type(vacationRequest.getVacType())
				.start_time(vacationRequest.getStartTime())
				.end_time(vacationRequest.getEndTime())
				.build();
	}
}
