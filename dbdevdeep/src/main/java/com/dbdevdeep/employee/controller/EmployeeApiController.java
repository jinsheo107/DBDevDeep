package com.dbdevdeep.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dbdevdeep.employee.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmployeeApiController {

	private final EmployeeService employeeService;
	
	@Autowired
	public EmployeeApiController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@PostMapping("/login")
    public String login(
            @RequestParam("emp_id") String empId,
            @RequestParam("emp_pw") String empPw,
            HttpSession session,
            Model model) {

        // 사용자 인증 처리 (예시: 하드코딩된 사용자 ID와 PW로 인증)
        // 실제 애플리케이션에서는 데이터베이스에서 사용자 정보를 확인해야 합니다.
        if ("admin".equals(empId) && "password".equals(empPw)) {
            session.setAttribute("user", empId); // 로그인 성공 시 세션에 사용자 정보 저장
            return "redirect:/home"; // 로그인 성공 후 리다이렉트할 페이지
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "login"; // 로그인 페이지로 다시 돌아가기
        }
    }
}
