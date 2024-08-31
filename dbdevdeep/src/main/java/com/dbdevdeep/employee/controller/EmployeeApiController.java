package com.dbdevdeep.employee.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbdevdeep.employee.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmployeeApiController {

	private final EmployeeService employeeService;

	@Autowired
	public EmployeeApiController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@ResponseBody
	@PostMapping("/govIdCheck")
	public Map<String, String> govIdCheck(@RequestBody String govId) {
		
		Map<String, String> resultMap = new HashMap<String, String>();
		
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "중복확인 중 오류가 발생하였습니다.");
		
		if(employeeService.govIdCheck(govId) == 1) {
			resultMap.put("res_code", "409");
			resultMap.put("res_msg", "중복되는 값이 존재합니다.");
		} else {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "중복되는 값이 없습니다.");
		}
		
		return resultMap;
	}
	
}
