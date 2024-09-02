 package com.dbdevdeep.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dbdevdeep.employee.domain.EmployeeDto;
import com.dbdevdeep.employee.domain.TeacherHistoryDto;
import com.dbdevdeep.employee.service.EmployeeService;
import com.dbdevdeep.employee.service.TeacherHistoryService;

@Controller
public class EmployeeViewController {
	
	private final EmployeeService employeeService;
	private final TeacherHistoryService teacherHistoryService;
	
	@Autowired
	public EmployeeViewController(EmployeeService employeeService, TeacherHistoryService teacherHistoryService) {
		this.employeeService = employeeService;
		this.teacherHistoryService = teacherHistoryService;
	}

	@GetMapping("/login")
	public String loginPage() {
		return "employee/login";
	}
	
	@GetMapping("/signup")
	public String createEmployeePage() {
		return"employee/signup";
	}
	
	@GetMapping("/addressBook")
	public String selectAddressbookList(Model model, EmployeeDto dto) {
		
		List<EmployeeDto> resultList = employeeService.selectEmployeeList(dto);
		
		model.addAttribute("resultList", resultList);
		
		return "employee/addressBook";
	}
	
	@GetMapping("/classByYear")
	public String selectClassByYearList(Model model, TeacherHistoryDto dto) {
		List<TeacherHistoryDto> resultList = teacherHistoryService.selectClassByYearList();
		
		model.addAttribute("resultList", resultList);
		
		return "employee/classByYear";
	}
}
