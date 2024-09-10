package com.dbdevdeep.approve.domain;

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
public class TempEditDto {

	private Long temp_no;
	private String temp_name;
	private String temp_content;
	private int temp_type;
	
	public TempEdit toEntity() {
		return TempEdit.builder()
				.tempNo(temp_no)
				.tempName(temp_name)
				.tempContent(temp_content)
				.tempType(temp_type)
				.build();
	}
	
	public TempEditDto toDto(TempEdit tempEdit) {
		return TempEditDto.builder()
				.temp_no(tempEdit.getTempNo())
				.temp_name(tempEdit.getTempName())
				.temp_content(tempEdit.getTempContent())
				.temp_type(tempEdit.getTempType())
				.build();
	}
}
