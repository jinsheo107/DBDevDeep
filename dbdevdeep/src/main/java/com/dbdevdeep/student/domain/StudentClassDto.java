package com.dbdevdeep.student.domain;

import com.dbdevdeep.employee.domain.TeacherHistory;

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
	private Student student;
	private TeacherHistory teacher_history;
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
		        .student_no(studentClass.getStudent().getStudentNo())
		        .student(studentClass.getStudent())
		        .teacher_no(studentClass.getTeacherHistory().getTeacherNo())
		        .teacher_history(studentClass.getTeacherHistory())
		        .student_id(studentClass.getStudentId())
		        .build();
	}
}
