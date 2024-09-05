package com.dbdevdeep.place.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	
	 // 사용 불가 시작일과 종료일 데이터를 조회하여 화면에 전달
    @GetMapping("/place/dates")
    public String showDates(Model model) {
        List<String> unuseableStartDates = placeService.getFormattedUnuseableStartDate();
        List<String> unuseableEndDates = placeService.getFormattedUnuseableEndDate();
        
        model.addAttribute("unuseableStartDates", unuseableStartDates);
        model.addAttribute("unuseableEndDates", unuseableEndDates);

        return "place/dates"; // 뷰 파일 이름
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
