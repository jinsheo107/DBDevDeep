package com.dbdevdeep.place.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlaceViewController {

	@GetMapping("/place") //맵핑 주소적기
	public String placeListPage() {
		return "place/list";
	}
}
