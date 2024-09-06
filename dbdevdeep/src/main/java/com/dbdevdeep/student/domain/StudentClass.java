package com.dbdevdeep.student.domain;

import com.dbdevdeep.employee.domain.TeacherHistory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="class_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class StudentClass {
	
	@Id
	@Column(name="class_no")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long classNo;
	
	@ManyToOne
	@JoinColumn(name="student_no")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name="teacher_no")
	private TeacherHistory teacherHistory;
	
	@Column(name="student_id")
	private String studentId;
	
}
