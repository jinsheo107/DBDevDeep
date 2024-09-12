package com.dbdevdeep.schedule.domain;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class ScheduleDto {
	private Long schedule_no;
	private int calendar_type;
	
	private Long category_no;
	private String category_color;
	private String emp_id;
	
	private String schedule_title;
	private String schedule_content;
	private LocalDateTime start_time;
	private LocalDateTime end_time;
	private String is_all_day;
	private String schedule_place;
	private String repeat_type;
	private Date repeat_start_date;
	private Date repeat_end_date;
	private LocalDateTime reg_time;
	private LocalDateTime mod_time;
	
	public Schedule toEntity() {
		return Schedule.builder()
				.scheduleNo(schedule_no)
				.calendarType(calendar_type)
				.scheduleTitle(schedule_title)
				.scheduleContent(schedule_content)
				.startTIme(start_time)
				.endTime(end_time)
				.isAllDay(is_all_day)
				.schedulePlace(schedule_place)
				.repeatType(repeat_type)
				.repeatStartDate(repeat_start_date)
				.repeatEndDate(repeat_end_date)
				.regTime(reg_time)
				.modTime(mod_time)
				.build();
	}
	
	public ScheduleDto toDto(Schedule schedule) {
		return ScheduleDto.builder()
				.schedule_no(schedule.getScheduleNo())
				.calendar_type(schedule.getCalendarType())
				.category_no(schedule.getCategory().getCategoryNo())
				.category_color(schedule.getCategory().getCategoryColor())
				.emp_id(schedule.getEmployee().getEmpId())
				.schedule_title(schedule.getScheduleTitle())
				.schedule_content(schedule.getScheduleContent())
				.start_time(schedule.getStartTIme())
				.end_time(schedule.getEndTime())
				.is_all_day(schedule.getIsAllDay())
				.schedule_place(schedule.getSchedulePlace())
				.repeat_type(schedule.getRepeatType())
				.repeat_start_date(schedule.getRepeatStartDate())
				.repeat_end_date(schedule.getRepeatEndDate())
				.reg_time(schedule.getRegTime())
				.mod_time(schedule.getModTime())
				.build();
	}
}
