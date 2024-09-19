package com.dbdevdeep.schedule.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.repository.EmployeeRepository;
import com.dbdevdeep.schedule.domain.Category;
import com.dbdevdeep.schedule.domain.Schedule;
import com.dbdevdeep.schedule.domain.ScheduleDto;
import com.dbdevdeep.schedule.repository.CategoryRepository;
import com.dbdevdeep.schedule.repository.ScheduleRepository;

@Service
public class ScheduleService {
	private final ScheduleRepository scheduleRepository;
	private final EmployeeRepository employeeRepository;
	private final CategoryRepository categoryRepository;
	
	@Autowired
	public ScheduleService(ScheduleRepository scheduleRepository, EmployeeRepository employeeRepository,
			CategoryRepository categoryRepository) {
		this.scheduleRepository = scheduleRepository;
		this.employeeRepository = employeeRepository;
		this.categoryRepository = categoryRepository;
	}
	
	public List<ScheduleDto> selectTotalScheduleList(){
		List<Schedule> scheduleList = scheduleRepository.findAll();
		
		List<ScheduleDto> scheduleDtoList = new ArrayList<ScheduleDto>();
		for(Schedule s : scheduleList) {
			ScheduleDto dto = new ScheduleDto().toDto(s);
			scheduleDtoList.add(dto);
		}
		
		return scheduleDtoList;
	}
	
	public List<ScheduleDto> selectPublicScheduleList() {
		List<Schedule> scheduleList = scheduleRepository.findByCalendarType(0);
		
		List<ScheduleDto> scheduleDtoList = new ArrayList<ScheduleDto>();
		for(Schedule s : scheduleList) {
			ScheduleDto dto = new ScheduleDto().toDto(s);	
			scheduleDtoList.add(dto);
		}
		
		return scheduleDtoList;
	}

	public List<ScheduleDto> selectPrivateScheduleList(String empId) {
		List<Schedule> scheduleList = scheduleRepository.findByCalendarTypeAndEmployee_EmpId(1,empId);
		
		List<ScheduleDto> scheduleDtoList = new ArrayList<ScheduleDto>();
		for(Schedule s : scheduleList) {
			ScheduleDto dto = new ScheduleDto().toDto(s);
			scheduleDtoList.add(dto);
		}
		
		return scheduleDtoList;
	}

	public Schedule createSchedule(ScheduleDto dto) {
	    Employee employee = employeeRepository.findByempId(dto.getEmp_id());
	    Category category = categoryRepository.findByCategoryNo(dto.getCategory_no());
	    
	    if (employee == null || category == null) {
	        System.out.println("null값입니다.");
	    }
		
		Schedule schedule = Schedule.builder()
				.calendarType(dto.getCalendar_type())
				.scheduleTitle(dto.getSchedule_title())
				.scheduleContent(dto.getSchedule_content())
				.startDate(dto.getStart_date())
				.startTime(dto.getStart_time())
				.endDate(dto.getEnd_date())
				.endTime(dto.getEnd_time())
				.isAllDay(dto.getIs_all_day())
				.schedulePlace(dto.getSchedule_place())
				.repeatType(dto.getRepeat_type())
				.repeatStartDate(dto.getRepeat_start_date())
				.repeatEndDate(dto.getRepeat_end_date())
				.regTime(dto.getReg_time())
				.modTime(dto.getMod_time())
				.employee(employee)
				.category(category)
				.alertType(dto.getAlert_type())
				.build();
		
		return scheduleRepository.save(schedule);
	}

	public ScheduleDto selectScheduleOne(Long schedule_no) {
		Schedule schedule = scheduleRepository.findByScheduleNo(schedule_no);
		
		ScheduleDto dto = ScheduleDto.builder()
				.schedule_no(schedule.getScheduleNo())
				.calendar_type(schedule.getCalendarType())
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
				.emp_name(schedule.getEmployee().getEmpName())
				.category_color(schedule.getCategory().getCategoryColor())
				.category_name(schedule.getCategory().getCategoryName())
				.category_no(schedule.getCategory().getCategoryNo())
				.alert_type(schedule.getAlertType())
				.build();
		
		return dto;
	}

	public int deleteSchedule(Long schedule_no) {
		int result = 0;
		try {
			scheduleRepository.deleteById(schedule_no);
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Schedule updateschedule(ScheduleDto dto) {
		ScheduleDto temp = selectScheduleOne(dto.getSchedule_no());
		
		temp.setCalendar_type(dto.getCalendar_type());
		temp.setCategory_no(dto.getCategory_no());
		temp.setEmp_id(dto.getEmp_id());
		temp.setSchedule_title(dto.getSchedule_title());
		temp.setSchedule_content(dto.getSchedule_content());
		temp.setStart_date(dto.getStart_date());
		temp.setStart_time(dto.getStart_time());
		temp.setEnd_date(dto.getEnd_date());
		temp.setEnd_time(dto.getEnd_time());
		temp.setIs_all_day(dto.getIs_all_day());
		temp.setSchedule_place(dto.getSchedule_place());
		temp.setRepeat_type(dto.getRepeat_type());
		temp.setRepeat_start_date(dto.getRepeat_start_date());
		temp.setRepeat_end_date(dto.getRepeat_end_date());
		temp.setMod_time(LocalDateTime.now());
		temp.setAlert_type(dto.getAlert_type());
		
		Schedule schedule = temp.toEntity(employeeRepository, categoryRepository);
		Schedule result = scheduleRepository.save(schedule);
		
		return result;
	}

}
