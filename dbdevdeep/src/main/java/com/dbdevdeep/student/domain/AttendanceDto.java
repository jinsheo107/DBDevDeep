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
public class AttendanceDto {
	private Long attendance_no;
	
	private Long student_no;
	
	private Long emp_id;
	
	private String attendance_status;
	private String attendance_reason;
	private LocalDate attendance_date;
	private LocalDateTime reg_time;
	private LocalDateTime mod_time;
	
	public Attendance toEntity() {
		return Attendance.builder()
				.attendanceNo(attendance_no)
				.attendanceStatus(attendance_status)
				.attendanceReason(attendance_reason)
				.attendanceDate(attendance_date)
				.regTime(reg_time)
				.modTime(mod_time)
				.build();
	}
	
	public AttendanceDto toDto(Attendance att) {
		return AttendanceDto.builder()
				.attendance_no(att.getAttendanceNo())
				.attendance_status(att.getAttendanceStatus())
				.attendance_reason(att.getAttendanceReason())
				.attendance_date(att.getAttendanceDate())
				.reg_time(att.getRegTime())
				.mod_time(att.getModTime())
				.build();
	}
	
}
