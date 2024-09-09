 package com.dbdevdeep.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@GetMapping("/addressbook")
	public String selectAddressbookList(Model model, EmployeeDto dto) {
		
		List<EmployeeDto> resultList = employeeService.selectYEmployeeList();
		
		model.addAttribute("resultList", resultList);
		
		return "employee/address_book";
	}
	
	@GetMapping("/mypage")
	public String mypagePage() {
		return "employee/mypage";
	}
	
	@GetMapping("/mysign")
	public String mysignPage() {
		return "employee/mysign";
	}
	
	@ResponseBody
	@GetMapping("/check-pw/{pwd}")
	public Map<String, String> checkPw(@PathVariable("pwd") String pwd) {
		Map<String, String> resultMap = new HashMap<String, String>();
		
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "비밀번호 확인에 실패하였습니다.");
		
		System.out.println("pwd: " + pwd);
		
		if(employeeService.checkPw(pwd) > 0) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "비밀번호 확인에 성공하였습니다.");			
		} 
		
		return resultMap;
	}
	

}
