package com.dbdevdeep.approve.domain;

import java.time.LocalDate;

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
	private LocalDate start_date;
	private LocalDate end_date;
	
	public VacationRequest toEntity() {
		return VacationRequest.builder()
				.vacNo(vac_no)
				.approNo(appro_no)
				.vacYn(vac_yn)
				.vacType(vac_type)
				.startDate(start_date)
				.endDate(end_date)
				.build();
		
	}
	
	public VacationRequestDto toDto(VacationRequest vacationRequest) {
		return VacationRequestDto.builder()
				.vac_no(vacationRequest.getVacNo())
				.appro_no(vacationRequest.getApproNo())
				.vac_yn(vacationRequest.getVacYn())
				.vac_type(vacationRequest.getVacType())
				.start_date(vacationRequest.getStartDate())
				.end_date(vacationRequest.getEndDate())
				.build();
	}
}
