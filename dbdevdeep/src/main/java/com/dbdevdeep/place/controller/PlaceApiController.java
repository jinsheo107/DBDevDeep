package com.dbdevdeep.place.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dbdevdeep.place.domain.PlaceDto;
import com.dbdevdeep.place.service.PlaceFileService;
import com.dbdevdeep.place.service.PlaceService;

@Controller
public class PlaceApiController {

	private final PlaceService placeService;
	private final PlaceFileService placeFileService;
	
	@Autowired
	public PlaceApiController(PlaceService placeService, PlaceFileService placeFileService) {
		this.placeService = placeService;
		this.placeFileService = placeFileService;
		
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
	public Map<String, String> updatePlace(PlaceDto dto, @RequestParam(name ="file", required=false) MultipartFile file){
		Map<String, String> resultMap = new HashMap<String, String>();
		
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "게시글 수정중 오류가 발생하였습니다.");
		
		try {
	        // 파일이 존재하는 경우 처리
	        if (file != null && !file.isEmpty()) {
	            String originalFilename = file.getOriginalFilename();

	            if (originalFilename != null && !originalFilename.isEmpty()) {
	                // 새로운 파일 업로드
	                String savedFileName = placeFileService.upload(file);

	                if (savedFileName != null) {
	                    // 기존 파일 삭제 (기존 파일이 있을 경우)
	                    if (dto.getNew_pic_name() != null && !dto.getNew_pic_name().isEmpty()) {
	                        placeFileService.delete(dto.getPlace_no());
	                    }

	                    // 새로운 파일 정보 DTO에 설정
	                    dto.setOri_pic_name(originalFilename);
	                    dto.setNew_pic_name(savedFileName);
	                } else {
	                    resultMap.put("res_msg", "파일 업로드 중 오류가 발생했습니다.");
	                    return resultMap;
	                }
	            }
	        }

	        // 장소 정보 수정
	        if (placeService.updatePlace(dto, file) > 0) {
	            resultMap.put("res_code", "200");
	            resultMap.put("res_msg", "게시글이 성공적으로 수정되었습니다.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return resultMap;
	}
	
	// 등록글 
	@ResponseBody
	@PostMapping("/place")
	public Map<String, String> createPlace(@RequestParam("file") MultipartFile file,
		PlaceDto dto) {
	    Map<String, String> resultMap = new HashMap<String, String>();
	    
	    resultMap.put("res_code", "404");
	    resultMap.put("res_msg", "장소 등록 중 오류가 발생하였습니다.");
	    
	    try {
	        // 파일이 있을 경우 처리
	        if (file != null && !file.isEmpty()) {
	            String originalFilename = file.getOriginalFilename();

	            if (originalFilename != null && !originalFilename.isEmpty()) {
	                String savedFileName = placeFileService.upload(file);

	                if (savedFileName != null) {
	                    dto.setOri_pic_name(originalFilename);
	                    dto.setNew_pic_name(savedFileName);
	                }
	            } else {
	                resultMap.put("res_msg", "파일 이름이 유효하지 않습니다.");
	                return resultMap;
	            }
	        }

	        // 장소 정보 생성
	        if (placeService.createPlace(dto, file) > 0) {
	            resultMap.put("res_code", "200");
	            resultMap.put("res_msg", "장소가 성공적으로 등록되었습니다.");
	        } else {
	            resultMap.put("res_msg", "장소 등록에 실패하였습니다.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return resultMap;
	}

}