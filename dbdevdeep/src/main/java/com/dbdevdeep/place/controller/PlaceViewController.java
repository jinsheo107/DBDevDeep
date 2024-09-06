package com.dbdevdeep.place.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
