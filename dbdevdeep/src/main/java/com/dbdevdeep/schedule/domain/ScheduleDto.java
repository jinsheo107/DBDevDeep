package com.dbdevdeep.schedule.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.repository.EmployeeRepository;
import com.dbdevdeep.schedule.repository.CategoryRepository;

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
	private String category_name;
	private String emp_id;
	private String emp_name;
	
	private String schedule_title;
	private String schedule_content;
	private LocalDate start_date;
	private LocalTime start_time;
	private LocalDate end_date;
	private LocalTime end_time;
	private String is_all_day;
	private String schedule_place;
	private String repeat_type;
	private LocalDate repeat_start_date;
	private LocalDate repeat_end_date;
	private LocalDateTime reg_time;
	private LocalDateTime mod_time;
	private String alert_type;
	
	public Schedule toEntity(EmployeeRepository employeeRepository,
			CategoryRepository categoryRepository) {
		Employee employee = employeeRepository.findByempId(emp_id);
		Category category = categoryRepository.findByCategoryNo(category_no);
		
		return Schedule.builder()
				.scheduleNo(schedule_no)
				.calendarType(calendar_type)
				.scheduleTitle(schedule_title)
				.scheduleContent(schedule_content)
				.startDate(start_date)
				.startTime(start_time)
				.endDate(end_date)
				.endTime(end_time)
				.isAllDay(is_all_day)
				.schedulePlace(schedule_place)
				.repeatType(repeat_type)
				.repeatStartDate(repeat_start_date)
				.repeatEndDate(repeat_end_date)
				.regTime(reg_time)
				.modTime(mod_time)
				.alertType(alert_type)
				.employee(employee)
				.category(category)
				.build();
	}
	
	public ScheduleDto toDto(Schedule schedule) {
		return ScheduleDto.builder()
				.schedule_no(schedule.getScheduleNo())
				.calendar_type(schedule.getCalendarType())
				.category_no(schedule.getCategory().getCategoryNo())
				.category_color(schedule.getCategory().getCategoryColor())
				.emp_name(schedule.getEmployee().getEmpName())
				.schedule_title(schedule.getScheduleTitle())
				.schedule_content(schedule.getScheduleContent())
				.start_date(schedule.getStartDate())
				.start_time(schedule.getStartTime())
				.end_date(schedule.getEndDate())
				.end_time(schedule.getEndTime())
				.is_all_day(schedule.getIsAllDay())
				.schedule_place(schedule.getSchedulePlace())
				.repeat_type(schedule.getRepeatType())
				.repeat_start_date(schedule.getRepeatStartDate())
				.repeat_end_date(schedule.getRepeatEndDate())
				.reg_time(schedule.getRegTime())
				.mod_time(schedule.getModTime())
				.alert_type(schedule.getAlertType())
				.build();
	}
}
