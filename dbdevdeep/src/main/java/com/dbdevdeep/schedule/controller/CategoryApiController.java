package com.dbdevdeep.schedule.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbdevdeep.schedule.domain.CategoryDto;
import com.dbdevdeep.schedule.service.CategoryService;

@Controller
public class CategoryApiController {
	
	private final CategoryService categoryService;
	
	@Autowired
	public CategoryApiController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@ResponseBody
	@PostMapping("/category")
	public Map<String,String> createCategory(@RequestBody CategoryDto dto){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String empId = user.getUsername();
		
		dto.setEmp_id(empId);
		
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "범주 등록 중 오류가 발생하였습니다.");
		
		if(categoryService.createCategory(dto) != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "범주가 성공적으로 등록되었습니다.");
		} else {
			resultMap.put("res_msg", "범주 등록 중 예외가 발생하였습니다.");
		}
		
		return resultMap;
	}
	
	@ResponseBody
	@DeleteMapping("/category/{category_no}")
	public Map<String,String> deleteCategory(@PathVariable("category_no") Long category_no){
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "범주 삭제 중 오류가 발생하였습니다.");
		
		if(categoryService.deleteCategory(category_no) > 0) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "범주가 성공적으로 삭제되었습니다.");
		} 
		
		return resultMap;
	}
	
	@ResponseBody
	@PutMapping("/category/{category_no}")
	public Map<String,String> updateCategory(@RequestBody CategoryDto dto){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String empId = user.getUsername();
		
		dto.setEmp_id(empId);
		
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "범주 수정 중 오류가 발생하였습니다.");
		
		if(categoryService.updateCategory(dto) != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "범주가 성공적으로 수정되었습니다.");
		} 
		
		return resultMap;
	}
}
