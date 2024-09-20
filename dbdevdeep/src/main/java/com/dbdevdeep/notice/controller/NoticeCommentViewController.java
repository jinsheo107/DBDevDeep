package com.dbdevdeep.notice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbdevdeep.employee.domain.EmployeeDto;
import com.dbdevdeep.employee.service.EmployeeService;
import com.dbdevdeep.notice.dto.NoticeCommentDto;
import com.dbdevdeep.notice.service.NoticeCommentService;

@Controller
public class NoticeCommentViewController {
	
	private final NoticeCommentService noticeCommentService;
	private final EmployeeService employeeService;
	@Autowired
	public NoticeCommentViewController(NoticeCommentService noticeCommentService, 
			EmployeeService employeeService) {
		this.noticeCommentService = noticeCommentService;
		this.employeeService = employeeService;
	}
	
	// 댓글 리스트 출력
	@GetMapping("/comment/list/{noticeNo}")
	public String selectNoticeCommentList(@PathVariable("noticeNo") Long noticeNo, Model model) {
		
		// 1. 로그인한 사용자의 정보 불러오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal(); // security 입장의 name(id), pw
		String read_id = user.getUsername();
		EmployeeDto readDto = employeeService.selectEmployeeOne(read_id);
		
		model.addAttribute("readDto", readDto);
		
		// 3. 댓글 리스트 조회
        List<NoticeCommentDto> cmtDtoList = noticeCommentService.selectNoticeCommentList(noticeNo);
        model.addAttribute("cmtDtoList", cmtDtoList);
        
	    // HTML 템플릿을 렌더링해서 반환 (Thymeleaf 예시)
	    return "notice/detail :: comment_place";  // 부분 HTML을 반환
	    
	}
	
	// 댓글 조회
	@ResponseBody
	@GetMapping("/comments/{cmtNo}")
	public NoticeCommentDto selectNoticeCommentOne(@PathVariable("cmtNo") Long cmtNo) {
		
		// 1. 로그인한 사용자의 정보 불러오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal(); // security 입장의 name(id), pw
		String read_id = user.getUsername();
		EmployeeDto readDto = employeeService.selectEmployeeOne(read_id);
		
		// 3. 댓글 조회
        NoticeCommentDto cmtDto = noticeCommentService.selectNoticeCommentOne(cmtNo);
        
        return cmtDto;
			    
	}
	
}
