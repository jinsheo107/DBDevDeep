package com.dbdevdeep.notice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbdevdeep.notice.dto.NoticeDto;
import com.dbdevdeep.notice.service.NoticeService;

@Controller
public class NoticeApiController {
	
	private final NoticeService noticeService;
	
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
	
	// 공지사항 게시글 수정
	@ResponseBody
	@PostMapping("/notice/edit/{notice_no}")
	public Map<String,String> updateNotice(@RequestBody NoticeDto dto){
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "게시글 작성 중 오류가 발생하였습니다.");
		
		if(noticeService.updateNotice(dto)>0) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "게시글 수정에 성공하였습니다.");
		}
		
		return resultMap;
	}
	
	// 공지사항 게시글 삭제
	@ResponseBody
	@DeleteMapping("/notice/{notice_no}")
	public Map<String,String> deleteNotice(@PathVariable("notice_no") Long notice_no){
		Map<String,String> map = new HashMap<String, String>();
		map.put("res_code", "404");
		map.put("res_msg", "게시글 삭제 중 오류가 발생했습니다.");
		
		if(noticeService.deleteNotice(notice_no) > 0) {
			map.put("res_code", "200");
			map.put("res_msg", "정상적으로 게시글이 삭제되었습니다.");			
		}
		return map;
	}
	
}
