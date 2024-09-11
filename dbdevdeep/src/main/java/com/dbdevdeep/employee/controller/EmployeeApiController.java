package com.dbdevdeep.employee.controller;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dbdevdeep.FileService;
import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.domain.EmployeeDto;
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
	
	// 상태 메시지 수정
	@ResponseBody
    @PostMapping("/status/{empId}")
    public Map<String,String> updateStatus(@RequestBody EmployeeDto dto){
		Map<String,String> map = new HashMap<String, String>();
		map.put("res_code", "404");
		map.put("res_msg", "게시글 삭제 중 오류가 발생했습니다.");
		
		int result = employeeService.editChatStatus(dto.getEmp_id(), dto.getChat_status_msg());
		
		if(result>0) {
			map.put("res_code", "200");
			map.put("res_msg", "상태메세지가 수정되었습니다.");
		}
		
		return map;
	}
	
}
