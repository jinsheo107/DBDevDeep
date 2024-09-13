package com.dbdevdeep.schedule.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbdevdeep.schedule.domain.Schedule;
import com.dbdevdeep.schedule.domain.ScheduleDto;
import com.dbdevdeep.schedule.repository.ScheduleRepository;

@Service
public class ScheduleService {
	private final ScheduleRepository scheduleRepository;
	
	@Autowired
	public ScheduleService(ScheduleRepository scheduleRepository) {
		this.scheduleRepository = scheduleRepository;
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

	
	
}
