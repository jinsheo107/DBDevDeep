package com.dbdevdeep.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbdevdeep.employee.domain.TeacherHistory;
import com.dbdevdeep.employee.domain.TeacherHistoryDto;
import com.dbdevdeep.employee.repository.TeacherHistoryRepository;

@Service
public class TeacherHistoryService {

	private final TeacherHistoryRepository teacherHistoryRepository;
	
	@Autowired
	public TeacherHistoryService(TeacherHistoryRepository teacherHistoryRepository) {
		this.teacherHistoryRepository = teacherHistoryRepository;
	}
	
	public List<TeacherHistoryDto> selectClassByYearList() {
		List<TeacherHistory> teacherHistoryList = teacherHistoryRepository.findAll();
		
		List<TeacherHistoryDto> teacherHistoryDtoList = new ArrayList<TeacherHistoryDto>();
		for(TeacherHistory t : teacherHistoryList) {
			TeacherHistoryDto dto = new TeacherHistoryDto().toDto(t);
			dto.setTeach_emp_id(t.getEmployee().getEmpId());
			dto.setTeach_emp_name(t.getEmployee().getEmpName());
			teacherHistoryDtoList.add(dto);
		}
		
		return teacherHistoryDtoList;
	}
}
