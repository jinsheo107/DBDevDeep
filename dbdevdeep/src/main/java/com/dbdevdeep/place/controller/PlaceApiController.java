package com.dbdevdeep.place.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dbdevdeep.FileService;
import com.dbdevdeep.place.domain.PlaceDto;
import com.dbdevdeep.place.service.PlaceService;

@Controller
public class PlaceApiController {

	private final PlaceService placeService;
	private final FileService fileService;
	
	@Autowired
	public PlaceApiController(PlaceService placeService, FileService fileService) {
		this.placeService = placeService;
		this.fileService = fileService;
		
	}
	
	// 등록글 
	@ResponseBody
	@PostMapping("/place")
	public Map<String, String> createPlace(@ModelAttribute PlaceDto dto,
	                                       @RequestParam("file") MultipartFile file) {
	    Map<String, String> resultMap = new HashMap<>();
	    
	    dto.setPlace_no(0L);  // 0번부터 시작
	    
	    String savedFileName = fileService.employeePicUpload(file);
	    
	    if (savedFileName != null) {
	        dto.setOri_pic_name(file.getOriginalFilename());
	        dto.setNew_pic_name(savedFileName);
	        if (placeService.createPlace(dto) != null) {
	            resultMap.put("res_code", "200");
	            resultMap.put("res_msg", "게시글이 성공적으로 등록되었습니다.");
	        } else {
	            resultMap.put("res_msg", "게시글 등록에 실패하였습니다.");
	        }
	    } else {
	        resultMap.put("res_msg", "파일 업로드가 실패하였습니다.");
	    }
	    
	    return resultMap;
	}
}
