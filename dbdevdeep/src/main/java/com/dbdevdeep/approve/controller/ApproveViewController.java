package com.dbdevdeep.approve.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApproveViewController {

	@GetMapping("/approve")
	public String selectApproveList() {
		return "approve/approList";
	}
	
	@GetMapping("/approCreate")
    public String showApproCreatePage() {
        return "approve/approCreate";
	}
}
