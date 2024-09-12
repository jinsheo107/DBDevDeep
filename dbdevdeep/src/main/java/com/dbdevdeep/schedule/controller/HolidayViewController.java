package com.dbdevdeep.schedule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbdevdeep.schedule.domain.HolidayDto;
import com.dbdevdeep.schedule.service.HolidayService;

@Controller
public class HolidayViewController {
	
	private final HolidayService holidayService;
	
	@Autowired
	public HolidayViewController(HolidayService holidayService) {
		this.holidayService = holidayService;
	}
	
	@GetMapping("/holiday")
	public String selectHolidayList(Model model) {
		List<HolidayDto> resultList = holidayService.selectHolidayList();
		
		model.addAttribute("resultList", resultList);
		
		return "schedule/holiday";
	}
	
	@ResponseBody
	@GetMapping("/holiday/{holiday_no}")
	public HolidayDto selectHolidayOne(@PathVariable("holiday_no") Long holiday_no) {
		HolidayDto dto = holidayService.selectHolidayOne(holiday_no);
		
		return dto;
	}
	
}