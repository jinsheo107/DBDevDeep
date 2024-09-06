package com.dbdevdeep.approve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	public String selectApproveList(Model model , ApproveDto dto) {
		
		List<ApproveDto> resultList = approveService.selectApproveList(dto);
		model.addAttribute("resultList",resultList);
		return "approve/approList";
	}
	
	@GetMapping("/approCreate")
    public String showApproCreatePage() {
        return "approve/approCreate";
	}
	
	
}
