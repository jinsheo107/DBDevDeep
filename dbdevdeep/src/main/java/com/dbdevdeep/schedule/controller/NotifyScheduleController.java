package com.dbdevdeep.schedule.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotifyScheduleController {
	
	@GetMapping("/schedule/notify")
	public ResponseEntity<String> notifySchedule(@RequestParam String title, 
	                                              @RequestParam String message, 
	                                              @RequestParam String time) {
	    // 알림 정보를 로깅
	    System.out.println("알림 수신: 제목 = " + title + ", 메시지 = " + message + ", 시간 = " + time);
	    
	    return ResponseEntity.ok("알림이 성공적으로 처리되었습니다.");
	}
}
