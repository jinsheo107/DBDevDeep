package com.dbdevdeep.student.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dbdevdeep.student.domain.StudentDto;
import com.dbdevdeep.student.service.StudentFileService;
import com.dbdevdeep.student.service.StudentService;

@Controller
public class StudentApiController {
	
	// 의존성 주입
	private final StudentFileService studentFileService;
	private final StudentService studentService;
	
	@Autowired
	public StudentApiController(StudentService studentService, StudentFileService studentFileService) {
		this.studentService = studentService;
		this.studentFileService = studentFileService;
	}
	
	// 게시글 등록
	@ResponseBody
	@PostMapping("/student")
	public Map<String,String> createStudent(StudentDto dto,
			@RequestParam("file") MultipartFile file){
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "게시글 등록중 오류가 발생했습니다.");
		String savedFileName = studentFileService.upload(file);
		if(savedFileName != null) {
			dto.setStudent_ori_profile(file.getOriginalFilename());
			dto.setStudent_new_profile(savedFileName);
			if(studentService.createStudent(dto) != null) {
				resultMap.put("res_code", "200");
				resultMap.put("res_msg", "게시글이 성공적으로 등록되었습니다.");
			}else {
				resultMap.put("res_msg", "파일 업로드가 실패하였습니다.");
			}
		}
		return resultMap;
	}
	
}
