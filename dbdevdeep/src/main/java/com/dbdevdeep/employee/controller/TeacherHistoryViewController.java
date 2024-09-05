package com.dbdevdeep.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dbdevdeep.employee.domain.TeacherHistoryDto;
import com.dbdevdeep.employee.service.TeacherHistoryService;
import com.dbdevdeep.employee.vo.GradeClassGroup;

@Controller
public class TeacherHistoryViewController {

	private final TeacherHistoryService teacherHistoryService;
	
	@Autowired
	public TeacherHistoryViewController(TeacherHistoryService teacherHistoryService) {
		this.teacherHistoryService = teacherHistoryService;
	}
	
	@GetMapping("/class-year")
	public String GroupByYearList(Model model, GradeClassGroup group) {
		List<GradeClassGroup> resultList = teacherHistoryService.GroupByYearList();
		
		model.addAttribute("resultList", resultList);
		
		return "employee/class_by_year";
	}
	
	@GetMapping("/class-year/{t_year}")
	public String selectClassByYearList(@PathVariable("t_year") String t_year, Model model) {
		List<TeacherHistoryDto> resultList = teacherHistoryService.selectClassByYearList(t_year);
		
		model.addAttribute("resultList", resultList);
		
		return "employee/grade_class_detail";
	}
	
}
