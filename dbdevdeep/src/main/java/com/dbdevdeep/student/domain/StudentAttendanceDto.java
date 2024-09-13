package com.dbdevdeep.student.domain;

import java.time.LocalDate;
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
public class StudentAttendanceDto {
	private Long attendance_no;
	
	private Long student_no;
	
	private Long emp_id;
	
	private String attendance_status;
	private String attendance_reason;
	private LocalDate attendance_date;
	private LocalDateTime reg_time;
	private LocalDateTime mod_time;
	
	public StudentAttendance toEntity() {
		return StudentAttendance.builder()
				.attendanceNo(attendance_no)
				.attendanceStatus(attendance_status)
				.attendanceReason(attendance_reason)
				.attendanceDate(attendance_date)
				.regTime(reg_time)
				.modTime(mod_time)
				.build();
	}
	
	public StudentAttendanceDto toDto(StudentAttendance att) {
		return StudentAttendanceDto.builder()
				.attendance_no(att.getAttendanceNo())
				.attendance_status(att.getAttendanceStatus())
				.attendance_reason(att.getAttendanceReason())
				.attendance_date(att.getAttendanceDate())
				.reg_time(att.getRegTime())
				.mod_time(att.getModTime())
				.build();
	}
	
}
