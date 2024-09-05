package com.dbdevdeep.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentViewController {
	
	@GetMapping("/student")
	public String studentMainPage() {
		return "student/student_homepage";
	}
	
	@GetMapping("/student/create")
	public String createStudentPage() {
		return "student/student_create";
	}
	
}
