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
public class ReferenceDto {

	private Long ref_no;
	private Long appro_no;
	private String emp_id;
	
	public Reference toEntity() {
		return Reference.builder()
				.refNo(ref_no)
				.build();
				
	}
	
	public ReferenceDto toDto(Reference reference) {
	return ReferenceDto.builder()	
		.ref_no(reference.getRefNo())
		.build();
	}
}
