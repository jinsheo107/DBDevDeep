package com.dbdevdeep.employee.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

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
public class TeacherHistoryDto {
	private Long teacher_no;
	private String teach_emp_id;
	private String teach_emp_name;
	private int grade;
	private int grade_class;
	private String t_year;
	
	public TeacherHistory toEntity() {
		return TeacherHistory.builder()
				.teacherNo(teacher_no).grade(grade)
				.gradeClass(grade_class).tYear(t_year).build();
	}
	
	public TeacherHistoryDto toDto(TeacherHistory teacherHistory) {
		return TeacherHistoryDto.builder()
				.teacher_no(teacherHistory.getTeacherNo()).grade(teacherHistory.getGrade())
				.grade_class(teacherHistory.getGradeClass()).t_year(teacherHistory.getTYear())
				.build();
	}
}
