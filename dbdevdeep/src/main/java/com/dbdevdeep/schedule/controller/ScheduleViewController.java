package com.dbdevdeep.schedule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbdevdeep.schedule.domain.CategoryDto;
import com.dbdevdeep.schedule.domain.HolidayDto;
import com.dbdevdeep.schedule.domain.ScheduleDto;
import com.dbdevdeep.schedule.service.CategoryService;
import com.dbdevdeep.schedule.service.HolidayService;
import com.dbdevdeep.schedule.service.ScheduleService;

@Controller
public class ScheduleViewController {
	
	private final CategoryService categoryService;
	private final HolidayService holidayService;
	private final ScheduleService scheduleService;
	
	@Autowired
	public ScheduleViewController(CategoryService categoryService, HolidayService holidayService, ScheduleService scheduleService) {
		this.categoryService = categoryService;
		this.holidayService = holidayService;
		this.scheduleService = scheduleService;
	}
	
	@GetMapping("/schedule")
	public String selectscheduleList(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String empId = user.getUsername();
		
		List<CategoryDto> publicCategoryList = categoryService.selectPublicCategoryList();
		List<CategoryDto> privateCategoryList = categoryService.selectPrivateCategoryList(empId);
		model.addAttribute("publicCategoryList", publicCategoryList);
		model.addAttribute("privateCategoryList", privateCategoryList);
		
		return "schedule/schedule";
	}
	
    // 새로운 JSON 반환 메서드
    @GetMapping("/getHolidayData")
    @ResponseBody // JSON 형태로 반환
    public List<HolidayDto> getHolidayData() {
        return holidayService.selectHolidayList(); // 공휴일 데이터를 JSON으로 반환
    }
    
    // 전체 일정 데이터 반환 메서드
    @GetMapping("/getTotalScheduleData")
    @ResponseBody
    public List<ScheduleDto> getTotalScheduleData(){
    	return scheduleService.selectTotalScheduleList();
    }
    
    // 공용 일정 데이터 반환 메서드
    @GetMapping("/getPublicScheduleData")
    @ResponseBody // JSON 형태로 반환
    public List<ScheduleDto> getPublicScheduleData() {
        return scheduleService.selectPublicScheduleList(); // 공용 일정 데이터를 JSON으로 반환
    }

    // 개인 일정 데이터 반환 메서드
    @GetMapping("/getPrivateScheduleData")
    @ResponseBody // JSON 형태로 반환
    public List<ScheduleDto> getPrivateScheduleData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        String empId = user.getUsername();

        return scheduleService.selectPrivateScheduleList(empId); // 개인 일정 데이터를 JSON으로 반환
    }
}
