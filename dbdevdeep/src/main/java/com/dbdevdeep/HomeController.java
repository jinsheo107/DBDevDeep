package com.dbdevdeep;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dbdevdeep.attendance.domain.AttendanceDto;
import com.dbdevdeep.attendance.service.AttendanceService;
import com.dbdevdeep.employee.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {
	
	private final AttendanceService attendanceService;
	
	@GetMapping({"", "/"})
	public String home(Model model) {
		
		AttendanceDto dto = attendanceService.findByTodayCheckTime();
		
		if(dto != null) {
			model.addAttribute("checktime", dto);			
		}
		
		return "home";
	}
}
