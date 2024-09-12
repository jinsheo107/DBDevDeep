package com.dbdevdeep.employee.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dbdevdeep.FileService;
import com.dbdevdeep.employee.domain.EmployeeDto;
import com.dbdevdeep.employee.domain.MySignDto;
import com.dbdevdeep.employee.service.EmployeeService;

@Controller
public class EmployeeApiController {

	private final EmployeeService employeeService;
	private final FileService fileService;

	@Autowired
	public EmployeeApiController(EmployeeService employeeService, FileService fileService) {
		this.employeeService = employeeService;
		this.fileService = fileService;
	}
	
	@ResponseBody
	@PostMapping("/govid")
	public Map<String, String> govIdCheck(@RequestBody String govId) {
		
		Map<String, String> resultMap = new HashMap<String, String>();
		
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "중복확인 중 오류가 발생하였습니다.");
		
		if(employeeService.govIdCheck(govId) == 1) {
			resultMap.put("res_code", "409");
			resultMap.put("res_msg", "중복되는 값이 존재합니다.");
		} else {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "중복되는 값이 없습니다.");
		}
		
		return resultMap;
	}
	
	@ResponseBody
	@PostMapping("/signup")
	public Map<String, String> signup(EmployeeDto dto, @RequestParam("file") MultipartFile file) {
		
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "계정 등록 중 오류가 발생하였습니다.");
		
		String savedFileName = fileService.employeePicUpload(file);
		
		if(savedFileName != null) {
			dto.setOri_pic_name(file.getOriginalFilename());
			dto.setNew_pic_name(savedFileName);
			
			if(employeeService.addEmployee(dto) > 0) {
				resultMap.put("res_code", "200");
				resultMap.put("res_msg", "계정 등록에 성공하였습니다.");
			}
		}else {
			resultMap.put("res_msg", "파일 업로드가 실패하였습니다.");
		}
		return resultMap;
	}
	
	@ResponseBody
	@PostMapping("/addsign")
	public Map<String, String> addSign(MySignDto dto, @RequestParam("file") MultipartFile file) {
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "계정 등록 중 오류가 발생하였습니다.");
		
		String savedFileName = fileService.employeeSignPicUpload(file);
		
		if(savedFileName != null) {
			dto.setOri_pic_name(file.getOriginalFilename());
			dto.setNew_pic_name(savedFileName);
			
			if(employeeService.employeeSignAdd(dto) > 0) {
				resultMap.put("res_code", "200");
				resultMap.put("res_msg", "계정 등록에 성공하였습니다.");
			}
		}else {
			resultMap.put("res_msg", "파일 업로드가 실패하였습니다.");
		}
		
		return resultMap;
	}
	
}
