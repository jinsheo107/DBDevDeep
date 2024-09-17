package com.dbdevdeep.notice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbdevdeep.employee.service.EmployeeService;
import com.dbdevdeep.notice.dto.NoticeCommentDto;
import com.dbdevdeep.notice.service.NoticeCommentService;

@Controller
public class NoticeCommentApiController {
	
	private final NoticeCommentService noticeCommentService;
	private final EmployeeService employeeService;
	@Autowired
	public NoticeCommentApiController(NoticeCommentService noticeCommentService, 
			EmployeeService employeeService) {
		this.noticeCommentService = noticeCommentService;
		this.employeeService = employeeService;
	}
	
	// 댓글 작성
	@ResponseBody
	@PostMapping("/notice/comment")
	public Map<String,String> createNoticeComment(@RequestBody NoticeCommentDto dto){
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "댓글 작성 중 오류가 발생하였습니다.");
		
		if(noticeCommentService.createNoticeComment(dto)>0) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "댓글 작성에 성공하였습니다.");
			resultMap.put("notice_no", dto.getNotice_no().toString());  // 게시글 번호 반환
			
		}
		
		return resultMap;
	}
	
	// 댓글 삭제(소프트삭제)
	@ResponseBody
	@PostMapping("/delete/{cmtNo}")
	public Map<String,String> updateNotice(@PathVariable("cmtNo") Long cmtNo){
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "댓글 삭제 중 오류가 발생하였습니다.");
		
		int result = noticeCommentService.deleteComment(cmtNo);
		if(result>0) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "댓글이 성공적으로 삭제되었습니다.");
			
		}
		
		return resultMap;
	}
	
	// 댓글 수정
	@ResponseBody
	@PostMapping("/comments/{cmtNo}")
	public Map<String,String> updateNoticeCommnet(@RequestBody NoticeCommentDto dto){
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "댓글 수정 중 오류가 발생하였습니다.");
		
		if(noticeCommentService.updateNoticeComment(dto)>0) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "댓글 작성에 성공하였습니다.");
			resultMap.put("notice_no", dto.getNotice_no().toString());  // 게시글 번호 반환
			
		}
		
		return resultMap;
	}

}
