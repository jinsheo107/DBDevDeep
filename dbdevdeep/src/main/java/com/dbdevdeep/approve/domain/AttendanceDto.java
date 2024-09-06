package com.dbdevdeep.approve.domain;

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

	private Long attend_no;
	private String emp_id;
	private LocalDate attend_date;
	private LocalDateTime check_in_time;
	private LocalDateTime check_out_time;
	private int work_status;
	private String late_status;
	
	public Attendance toEntity() {
		return Attendance.builder()
				.attendNo(attend_no)
				.attendDate(attend_date)
				.checkInTime(check_in_time)
				.checkOutTime(check_out_time)
				.workStatus(work_status)
				.lateStatus(late_status)
				.build();
	}
	
	public AttendanceDto toDto (Attendance attendance) {
		return AttendanceDto.builder()
				.attend_no(attendance.getAttendNo())
				.emp_id(attendance.getEmployee().getEmpId())
				.attend_date(attendance.getAttendDate())
				.check_in_time(attendance.getCheckInTime())
				.check_out_time(attendance.getCheckOutTime())
				.work_status(attendance.getWorkStatus())
				.late_status(attendance.getLateStatus())
				.build();
	}
}
