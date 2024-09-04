package com.dbdevdeep.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dbdevdeep.student.domain.StudentDto;
import com.dbdevdeep.student.service.StudentService;

@Controller
public class StudentViewController {
	
	private final StudentService studentService;
	
	@Autowired
	public StudentViewController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	// 학생관리 메인 페이지로 이동
	@GetMapping("/student")
	public String studentMainPage() {
		return "student/student_homepage";
	}
	
	// 학생등록 페이지로 이동
	@GetMapping("/student/create")
	public String createStudentPage() {
		return "student/student_create";
	}
	
	// 학생 목록 페이지로 이동
	@GetMapping("/student/list")
	public String listStudentPage(Model model, StudentDto dto) {
		List<StudentDto> resultList = studentService.selectStudentList(dto);
		model.addAttribute("resultList",resultList);
		return "student/student_list";
	}
	
	
}
