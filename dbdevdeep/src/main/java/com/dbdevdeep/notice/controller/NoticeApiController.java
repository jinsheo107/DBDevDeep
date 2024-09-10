package com.dbdevdeep.notice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbdevdeep.notice.dto.NoticeDto;
import com.dbdevdeep.notice.service.NoticeService;

@Controller
public class NoticeApiController {
	
	private final NoticeService noticeService;
//	private final FileService fileSeivice;
	
	@Autowired
	public NoticeApiController(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
	
	// 공지사항 게시글 작성
	@ResponseBody
	@PostMapping("/notice")
	public Map<String,String> createNotice(@RequestBody NoticeDto dto){
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "게시글 작성 중 오류가 발생하였습니다.");
		
		if(noticeService.createNotice(dto)>0) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "게시글 작성에 성공하였습니다.");
		}
		
		return resultMap;
	}
	
	
	
}
