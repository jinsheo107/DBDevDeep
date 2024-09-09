package com.dbdevdeep.schedule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbdevdeep.schedule.domain.CategoryDto;
import com.dbdevdeep.schedule.service.CategoryService;

@Controller
public class CategoryViewController {
	
	private final CategoryService categoryService;
	
	@Autowired
	public CategoryViewController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping("/category")
	public String selectCategoryList(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String empId = user.getUsername();
		
		List<CategoryDto> publicCategoryList = categoryService.selectPublicCategoryList();
		List<CategoryDto> privateCategoryList = categoryService.selectPrivateCategoryList(empId);
		
		model.addAttribute("publicCategoryList", publicCategoryList);
		model.addAttribute("privateCategoryList", privateCategoryList);
		
		return "schedule/category_list";
	}
	
	@ResponseBody
	@GetMapping("/category/{category_no}")
	public CategoryDto selectCategoryOne(@PathVariable("category_no") Long category_no) {
		CategoryDto dto = categoryService.selectCategoryOne(category_no);
		
		return dto;
	}
}
