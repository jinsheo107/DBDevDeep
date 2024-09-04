package com.dbdevdeep.employee.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="teacher_history")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class TeacherHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="teacher_no")
	private Long teacherNo;
	
	// employee의 emp_id로 join
	@ManyToOne
	@JoinColumn(name="emp_id")
	private Employee employee;
	
	@Column(name="grade")
	private int grade;
	
	@Column(name="grade_class")
	private int gradeClass;
	
	@Column(name="t_year")
	private String tYear;
}
