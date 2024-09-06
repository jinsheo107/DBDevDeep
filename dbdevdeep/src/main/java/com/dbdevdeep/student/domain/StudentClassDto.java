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
public class StudentClassDto {
	private Long class_no;
	
	private Long student_no;
	
	private Long teacher_no;
	
	private String student_id;
	
	public StudentClass toEntity() {
		return StudentClass.builder()
				.classNo(class_no)
				.studentId(student_id)
				.build();
	}
	
	public StudentClassDto toDto(StudentClass studentClass) {
		return StudentClassDto.builder()
				.class_no(studentClass.getClassNo())
				.student_id(studentClass.getStudentId())
				.build();
	}
}
