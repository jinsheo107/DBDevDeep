package com.dbdevdeep.notice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NoticeViewController {
	// 의존성 주입
	
	// 리스트 출력
	@GetMapping("/notice")
	public String selectNoticeList() {
		return "notice/list";
	}
	
}
