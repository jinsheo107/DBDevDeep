package com.dbdevdeep.student.domain;

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
public class ParentDto {
	private Long parent_no;
	private Long student_no;
	private String parent_name;
	private String parent_birth;
	private String parent_phone;
	private String parent_relation;
	
	public Parent toEntity() {
		return Parent.builder()
				.parentNo(parent_no)
				.parentName(parent_name)
				.parentBirth(parent_birth)
				.parentPhone(parent_phone)
				.parentPhone(parent_phone)
				.build();
	}
	
	public ParentDto toDto(Parent parent) {
		return ParentDto.builder()
				.parent_no(parent.getParentNo())
				.parent_name(parent.getParentName())
				.parent_birth(parent.getParentBirth())
				.parent_phone(parent.getParentPhone())
				.parent_relation(parent.getParentRelation())
				.build();
	}
	

}
