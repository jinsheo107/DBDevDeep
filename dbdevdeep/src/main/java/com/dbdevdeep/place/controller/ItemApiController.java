package com.dbdevdeep.place.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbdevdeep.place.domain.ItemDto;
import com.dbdevdeep.place.service.ItemService;

@Controller
public class ItemApiController {

	private final ItemService itemService;
	
	@Autowired
	public ItemApiController(ItemService itemService) {
		this.itemService = itemService;
	}
	
	// 일련번호 정규식 	
	 @PostMapping("/item/check-serial")
	    public ResponseEntity<Map<String, Boolean>> checkSerial(@RequestBody Map<String, Object> payload) {
		    String placeNoStr = payload.get("place_no").toString();
		    String serialNo = payload.get("item_serial_no").toString();

		    Map<String, Boolean> response = new HashMap<>();
		    
		    // placeNo가 빈 문자열이거나 null인 경우 처리
		    if (placeNoStr == null || placeNoStr.isEmpty()) {
		        response.put("exists", false);  // 기본적으로 false를 반환
		        return ResponseEntity.ok(response);
		    }

		    // Long으로 변환
		    Long placeNo;
		    try {
		        placeNo = Long.parseLong(placeNoStr);
		    } catch (NumberFormatException e) {
		        response.put("exists", false);  // 변환에 실패하면 false를 반환
		        return ResponseEntity.ok(response);
		    }

		    boolean exists = itemService.checkSerialExists(placeNo, serialNo);
		    response.put("exists", exists);
		    return ResponseEntity.ok(response);
	    }
	
	
	//등록 
	@ResponseBody
	@PostMapping("/item")
	public Map<String, String> createPlace(@RequestBody ItemDto dto) {
	    Map<String, String> resultMap = new HashMap<String, String>();
	    
	    resultMap.put("res_code", "404");
	    resultMap.put("res_msg", "기자재 등록 중 오류가 발생하였습니다.");
	    
	    if(itemService.createItem(dto) > 0) {
	    	resultMap.put("res_code", "200");
	    	resultMap.put("res_msg", "기자재 등록에 성공하였습니다.");
	    }
	    return resultMap;
	}
	
}
