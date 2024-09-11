package com.dbdevdeep.place.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbdevdeep.place.domain.PlaceDto;
import com.dbdevdeep.place.service.PlaceService;

@Controller
public class PlaceApiController {

	private final PlaceService placeService;
	
	
	@Autowired
	public PlaceApiController(PlaceService placeService) {
		this.placeService = placeService;
		
		
	}
	
	// 삭제하기
	@ResponseBody
	@DeleteMapping("/place/{place_no}")
	public Map<String, String> deletePlace(@PathVariable("place_no") Long place_no){
		Map<String, String> resultMap = new HashMap<String, String>();
		
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "게시글 삭제중 오류가 발생하였습니다.");
		
		if(placeService.deletePlace(place_no) > 0) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "게시글 삭제에 성공했습니다.");
		}
		return resultMap;
	}
	
	
	// 수정하기
	@ResponseBody
	@PostMapping("/place/update/{place_no}")
	public Map<String, String> updatePlace(@RequestBody PlaceDto dto){
		Map<String, String> resultMap = new HashMap<String, String>();
		
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "게시글 수정중 오류가 발생하였습니다.");
		
		if(placeService.updatePlace(dto) > 0) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "게시글 수정에 성공했습니다.");
		}
		return resultMap;
	}
	
	// 등록글 
	@ResponseBody
	@PostMapping("/place")
	public Map<String, String> createPlace(@RequestBody PlaceDto dto) {
	    Map<String, String> resultMap = new HashMap<String, String>();
	    
	    resultMap.put("res_code", "404");
	    resultMap.put("res_msg", "게시글 작성 중 오류가 발생하였습니다.");
	    
	    if(placeService.createPlace(dto) > 0) {
	    	resultMap.put("res_code", "200");
	    	resultMap.put("res_msg", "게시글 작성에 성공하였습니다.");
	    }
	    return resultMap;
	}
}
