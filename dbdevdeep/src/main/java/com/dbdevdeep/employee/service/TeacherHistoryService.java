package com.dbdevdeep.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbdevdeep.employee.domain.EmployeeDto;
import com.dbdevdeep.employee.domain.TeacherHistory;
import com.dbdevdeep.employee.domain.TeacherHistoryDto;
import com.dbdevdeep.employee.mybatis.mapper.TeacherHistoryVoMapper;
import com.dbdevdeep.employee.repository.TeacherHistoryRepository;
import com.dbdevdeep.employee.vo.GradeClassGroup;

@Service
public class TeacherHistoryService {

	private final TeacherHistoryRepository teacherHistoryRepository;
	private final TeacherHistoryVoMapper teacherHistoryVoMapper;
	
	@Autowired
	public TeacherHistoryService(TeacherHistoryRepository teacherHistoryRepository,
			TeacherHistoryVoMapper teacherHistoryVoMapper) {
		this.teacherHistoryRepository = teacherHistoryRepository;
		this.teacherHistoryVoMapper = teacherHistoryVoMapper;
	}
	
	public List<TeacherHistoryDto> selectClassByYearList(String t_year) {
		List<TeacherHistory> teacherHistoryList = teacherHistoryRepository.findByClassByYear(t_year);
		
		List<TeacherHistoryDto> teacherHistoryDtoList = new ArrayList<TeacherHistoryDto>();
		for(TeacherHistory t : teacherHistoryList) {
			TeacherHistoryDto dto = new TeacherHistoryDto().toDto(t);
			
			if (t.getEmployee() != null) {				
	            dto.setTeach_emp_id(t.getEmployee().getEmpId());
	            dto.setTeach_emp_name(t.getEmployee().getEmpName());
	        } else {
	        	dto.setTeach_emp_id(null);
	            dto.setTeach_emp_name("null");
	        }
			teacherHistoryDtoList.add(dto);
		}
		
		return teacherHistoryDtoList;
	}
	
	public List<GradeClassGroup> GroupByYearList() {
		List<GradeClassGroup> gradeClassGroupList = teacherHistoryVoMapper.selectGroupByYear();
		
		return gradeClassGroupList;
	}
	
	
	
	public int tYearCheck(String t_year) {
		int result = -1;
		
		int countYear = teacherHistoryRepository.findByTYearForCreateClass(t_year);
		
		if(countYear == 0) {
			result = 0; // 중복 X
		} else {
			result = 1; // 중복 O
		}
		
		return result;
	}
	
	public void saveTeacherHistory(int grade, int gradeClass, String t_year) {
		TeacherHistoryDto dto = new TeacherHistoryDto();
		
		dto.setGrade(grade);
		dto.setGrade_class(gradeClass);
		dto.setT_year(t_year);
		
		TeacherHistory th = dto.toEntity();
		
		teacherHistoryRepository.save(th);
	}
	
	
}
