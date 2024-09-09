package com.dbdevdeep.student.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.dbdevdeep.employee.domain.Employee;

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
@Table(name="student_attendance")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Attendance {
	@Id
	@Column(name="attendance_no")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long attendanceNo;
	
	@ManyToOne
	@JoinColumn(name="student_no")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name="emp_id")
	private Employee emp;
	
	@Column(name="attendance_status")
	private String attendanceStatus;
	
	@Column(name="attendance_reason")
	private String attendanceReason;
	
	@Column(name="attendance_date")
	private LocalDate attendanceDate;
	
	@Column(name="reg_time")
	private LocalDateTime regTime;
	
	@Column(name="mod_time")
	private LocalDateTime modTime;
}
