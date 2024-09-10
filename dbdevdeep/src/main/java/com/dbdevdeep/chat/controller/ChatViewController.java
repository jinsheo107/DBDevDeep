package com.dbdevdeep.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatViewController {
	
	// 채팅 페이지 이동
	@GetMapping("/chat")
	public String selectChatLsit() {
		return "chat/chatpage";
	}
}
