package com.dbdevdeep.place.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemViewController {

	@GetMapping("/item")
	public String selectItemList() {
		
		return "item/list";
	}
}
