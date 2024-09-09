package com.dbdevdeep.place.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.dbdevdeep.employee.domain.EmployeeDto;
import com.dbdevdeep.employee.service.EmployeeService;
import com.dbdevdeep.place.domain.PlaceDto;
import com.dbdevdeep.place.service.PlaceService;

@Controller
public class PlaceViewController {
	
	private final PlaceService placeService;
	private final EmployeeService employeeService;
	
	@Autowired
	public PlaceViewController(PlaceService placeService, EmployeeService employeeService) {
		this.placeService = placeService;
		this.employeeService = employeeService;
	}
	
	// 등록
	@GetMapping("/place/create")
	public String createPlace(Model model) {
		
		  
	    // 1. 로그인한 사람 정보
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String emp_id = null; // emp_id 초기화
	    
	    if (authentication != null && authentication.isAuthenticated()) {
	        User user = (User) authentication.getPrincipal();
	        emp_id = user.getUsername();  // 인증된 사용자의 emp_id를 가져옴
	        System.out.println("Logged in user: " + emp_id);
	    } else {
	        System.out.println("No authenticated user.");
	    }
	    
	    // 2. 사용자 정보 가져오기
	    EmployeeDto dto = employeeService.selectEmployeeOne(emp_id);
	    
	    // 3. 부서따른권한
	    boolean isAuthorized = authentication.getAuthorities().stream()
	    		.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("D3"));
	    
	    System.out.println("토큰"+isAuthorized);
	    model.addAttribute("isAuthorized", isAuthorized);
	    model.addAttribute("writer", dto);
	    
	    
		return "place/create";
	}
	
	
	
	
	// 조회
	@GetMapping("/place")
	public String selectPlaceList(Model model, PlaceDto dto) {
	// 리스트출력	
	List<PlaceDto> resultList = placeService.selectPlaceList(dto);
	model.addAttribute("resultList",resultList);
	return "place/list";
	}
}
