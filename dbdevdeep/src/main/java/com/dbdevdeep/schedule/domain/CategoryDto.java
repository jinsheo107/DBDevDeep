package com.dbdevdeep.schedule.domain;

import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.repository.EmployeeRepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CategoryDto {
	
	private Long category_no;
	private int category_type;
	private String category_name;
	private String category_color;
	private String is_default;
	
	private String emp_id;
	
	public Category toEntity(EmployeeRepository employeeRepository) {
		// Employee 엔티티 설정
	    Employee employee = null;
	    if (category_type == 1 && emp_id != null) { // 개인 범주이고 emp_id가 있는 경우
	        employee = employeeRepository.findByempId(emp_id);
	    }
	    
		return Category.builder()
				.categoryNo(category_no)
				.categoryType(category_type)
				.categoryName(category_name)
				.categoryColor(category_color)
				.isDefault(is_default)
				.employee(employee) // Employee 객체 설정
				.build();
	}
	
	public CategoryDto toDto(Category category) {
		return CategoryDto.builder()
				.category_no(category.getCategoryNo())
				.category_type(category.getCategoryType())
				.category_name(category.getCategoryName())
				.category_color(category.getCategoryColor())
				.is_default(category.getIsDefault())
				.emp_id(category.getEmployee() != null ? category.getEmployee().getEmpId() : null) // emp_id 설정
				.build();
	}
	
}
