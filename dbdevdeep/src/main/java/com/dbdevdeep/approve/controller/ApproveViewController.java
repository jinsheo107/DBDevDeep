package com.dbdevdeep.approve.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.dbdevdeep.approve.domain.ApproFileDto;
import com.dbdevdeep.approve.domain.ApproveDto;
import com.dbdevdeep.approve.service.ApproveService;

@Controller
public class ApproveViewController {

	private final ApproveService approveService;
	
	@Autowired
	public ApproveViewController(ApproveService approveService) {
		this.approveService = approveService;
	}
	
	//목록조회
	@GetMapping("/approve")
	public String selectApproveList(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
		
		List<ApproveDto> resultList = approveService.selectApproveList(username);
		model.addAttribute("resultList",resultList);
		return "approve/approList";
	}
	
	// 결재 작성
	@GetMapping("/approCreate")
    public String showApproCreatePage() {
        return "approve/approCreate";
	}
	
	// 결재 상세
	@GetMapping("/approDetail/{appro_no}")
	public String selectBoardOne(Model model, @PathVariable("appro_no") Long approNo) {
		Map<String, Object> detailMap = approveService.getApproveDetail(approNo);
		model.addAllAttributes(detailMap);
		return "approve/approDetail";
	}

	// 결재 수정
	@GetMapping("/approUpdate/{appro_no}")
	public String updateApproOne(Model model, @PathVariable("appro_no") Long approNo) {
		Map<String, Object> detailMap = approveService.getApproveDetail(approNo);
		model.addAllAttributes(detailMap);
		return "approve/approUpdate";
	}
	
}
