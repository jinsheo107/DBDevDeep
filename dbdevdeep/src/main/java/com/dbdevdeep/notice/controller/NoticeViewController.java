package com.dbdevdeep.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dbdevdeep.employee.domain.EmployeeDto;
import com.dbdevdeep.employee.service.EmployeeService;
import com.dbdevdeep.notice.dto.NoticeCommentDto;
import com.dbdevdeep.notice.dto.NoticeDto;
import com.dbdevdeep.notice.service.NoticeCommentService;
import com.dbdevdeep.notice.service.NoticeService;

@Controller
public class NoticeViewController {
	
	// 의존성 주입
	private final NoticeService noticeService;
	private final EmployeeService employeeService;
	private final NoticeCommentService noticeCommentService;
	@Autowired
	public NoticeViewController(NoticeService noticeService, EmployeeService employeeService, 
			NoticeCommentService noticeCommentService) {
		this.noticeService = noticeService;
		this.employeeService = employeeService;
		this.noticeCommentService = noticeCommentService;
	}

	// 리스트 출력
	@GetMapping("/notice")
	public String selectNoticeList(Model model, NoticeDto dto) {
		
		List<NoticeDto> resultList = noticeService.selectNoticeList(dto);
		
		model.addAttribute("resultList",resultList);
		return "notice/list";
	}
	
	// 새 글 작성 폼
	@GetMapping("/notice/new")
	public String createNotice(Model model) {
		
		// 1. 로그인한 사용자의 정보 불러오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal(); // security 입장의 name(id), pw
		String writer_id = user.getUsername();
		EmployeeDto dto = employeeService.selectEmployeeOne(writer_id);
		
		model.addAttribute("writer", dto);
		
		return "notice/new";
	}
	
	// 상세 조회
	@GetMapping("/notice/{notice_no}")
	public String detailNotice(Model model, @PathVariable("notice_no") Long notice_no) {
		
		// 1. 로그인한 사용자의 정보 불러오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal(); // security 입장의 name(id), pw
		String read_id = user.getUsername();
		EmployeeDto readDto = employeeService.selectEmployeeOne(read_id);
		
		model.addAttribute("readDto", readDto);
		
		// 2. 게시글 조회
		NoticeDto dto = noticeService.selectNoticeOne(notice_no);
		model.addAttribute("dto", dto);
		
		// 3. 댓글 조회
		List<NoticeCommentDto> cmtDtoList = noticeCommentService.selectNoticeCommentList(notice_no);
		model.addAttribute("cmtDtoList", cmtDtoList);
		
		if(dto!=null) {
			// 4. 읽음 확인 추가
			noticeService.readCheck(read_id, notice_no);			
		}
		
		return "notice/detail";
	}
	
	// 게시글 수정 폼
	@GetMapping("/notice/edit/{notice_no}")
	public String editNotice(Model model, @PathVariable("notice_no") Long notice_no) {
		
		// 1. 로그인한 사용자의 정보 불러오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal(); // security 입장의 name(id), pw
		String writer_id = user.getUsername();
		EmployeeDto writerDto = employeeService.selectEmployeeOne(writer_id);
		model.addAttribute("writerDto", writerDto);
		
		NoticeDto dto = noticeService.selectNoticeOne(notice_no);
		model.addAttribute("dto", dto);
		
		return "notice/edit";
	}
	
		
}
