package com.dbdevdeep.notice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbdevdeep.notice.dto.NoticeCommentDto;
import com.dbdevdeep.notice.service.NoticeCommentService;

@Controller
public class NoticeCommentApiController {
	
	private final NoticeCommentService noticeCommentService;
	@Autowired
	public NoticeCommentApiController(NoticeCommentService noticeCommentService) {
		this.noticeCommentService = noticeCommentService;
	}
	
	// 댓글 리스트 출력
	@GetMapping("/notice/comment/{noticeNo}")
	public String selectNoticeCommentList(@PathVariable("noticeNo") Long noticeNo, Model model) {
		
		// 댓글 리스트를 가져와 JSON 형식으로 반환
	    List<NoticeCommentDto> cmtDtoList = noticeCommentService.selectNoticeCommentList(noticeNo);
	    model.addAttribute("cmtDtoList",cmtDtoList);
	    // HTML 템플릿을 렌더링해서 반환 (Thymeleaf 예시)
	    return "notice/detail :: comment_place";  // 부분 HTML을 반환
	    
	}
	
	// 댓글 작성
	@ResponseBody
	@PostMapping("/notice/comment")
	public Map<String,String> createNoticeComment(@RequestBody NoticeCommentDto dto){
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "게시글 작성 중 오류가 발생하였습니다.");
		
		if(noticeCommentService.createNoticeComment(dto)>0) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "게시글 작성에 성공하였습니다.");
			resultMap.put("notice_no", dto.getNotice_no().toString());  // 게시글 번호 반환
			
		}
		
		return resultMap;
	}

}
