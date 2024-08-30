 package com.dbdevdeep.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeViewController {

	@GetMapping("/login")
	public String loginPage() {
		return "employee/login";
	}
	
	@GetMapping("/signup")
	public String createEmployeePage() {
		return"employee/signup";
	}
}
