package com.dbdevdeep.notice.controller;

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
import com.dbdevdeep.notice.dto.NoticeDto;
import com.dbdevdeep.notice.service.NoticeService;

@Controller
public class NoticeViewController {
	
	// 의존성 주입
	private final NoticeService noticeService;
	private final EmployeeService employeeService;
	@Autowired
	public NoticeViewController(NoticeService noticeService, EmployeeService employeeService) {
		this.noticeService = noticeService;
		this.employeeService = employeeService;
	}

	// 리스트 출력
	@GetMapping("/notice")
	public String selectNoticeList(Model model, NoticeDto dto) {
		
		List<NoticeDto> resultList = noticeService.selectNoticeList(dto);
		
		model.addAttribute("resultList",resultList);
		
		return "notice/list";
	}
	
	// 새 글 작성
	@GetMapping("/notice/create")
	public String createNotice(Model model) {
		
		// 1. 로그인한 사용자의 정보 불러오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal(); // security 입장의 name(id), pw
		String writer_id = user.getUsername();
		EmployeeDto dto = employeeService.selectEmployeeOne(writer_id);
		
		model.addAttribute("writer", dto);
		
		return "notice/create";
	}
	
}
