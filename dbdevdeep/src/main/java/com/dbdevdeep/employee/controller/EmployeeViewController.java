 package com.dbdevdeep.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dbdevdeep.employee.domain.EmployeeDto;
import com.dbdevdeep.employee.service.EmployeeService;

@Controller
public class EmployeeViewController {
	
	private final EmployeeService employeeService;
	
	@Autowired
	public EmployeeViewController(EmployeeService employeeService) {
		this.employeeService = employeeService;
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
}
