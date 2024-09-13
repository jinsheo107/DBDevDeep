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
public class SubjectDto {
	private Long subject_no;
	private String subject_name;
	private String subject_grade;
	private String subject_semester;
	private String subject_year;
	private String subject_teacher;
	
	public Subject toEntity() {
		return Subject.builder()
				.subjectNo(subject_no)
				.subjectName(subject_name)
				.subjectGrade(subject_grade)
				.subjectSemester(subject_semester)
				.subjectYear(subject_year)
				.subjectTeacher(subject_teacher)
				.build();
	}
	
	public SubjectDto toDto(Subject sub) {
		return SubjectDto.builder()
				.subject_no(sub.getSubjectNo())
				.subject_name(sub.getSubjectName())
				.subject_grade(sub.getSubjectGrade())
				.subject_semester(sub.getSubjectSemester())
				.subject_year(sub.getSubjectYear())
				.subject_teacher(sub.getSubjectTeacher())
				.build();
	}
	
	
	
}
