package com.dbdevdeep.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dbdevdeep.notice.domain.NoticeDto;
import com.dbdevdeep.notice.service.NoticeService;

@Controller
public class NoticeViewController {
	
	// 의존성 주입
	private final NoticeService noticeService;
	@Autowired
	public NoticeViewController(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	// 리스트 출력
	@GetMapping("/notice")
	public String selectNoticeList(Model model, NoticeDto dto) {
		
		List<NoticeDto> resultList = noticeService.selectNoticeList(dto);
		
		model.addAttribute("resultList",resultList);
		
		return "notice/list";
	}
	
}
