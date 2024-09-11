package com.dbdevdeep.schedule.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.repository.EmployeeRepository;
import com.dbdevdeep.schedule.domain.Category;
import com.dbdevdeep.schedule.domain.CategoryDto;
import com.dbdevdeep.schedule.repository.CategoryRepository;

@Service
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	private final EmployeeRepository employeeRepository;
	
	@Autowired
	public CategoryService(CategoryRepository categoryRepository, EmployeeRepository employeeRepository) {
		this.categoryRepository = categoryRepository;
		this.employeeRepository = employeeRepository;
	}
	
	public List<CategoryDto> selectPublicCategoryList() {
		List<Category> categoryList = categoryRepository.findByCategoryType(0);
		
		List<CategoryDto> categoryDtoList = new ArrayList<CategoryDto>();
		for(Category c : categoryList) {
			CategoryDto dto = new CategoryDto().toDto(c);
			categoryDtoList.add(dto);
		}
		return categoryDtoList;
	}

	public List<CategoryDto> selectPrivateCategoryList(String empId) {
        // 기본 개인 범주는 모든 사용자에게 보여야 함
        List<Category> defaultPersonalCategories = categoryRepository.findByCategoryTypeAndIsDefault(1, "Y");

        // 사용자의 개인 범주만 추가적으로 가져옴
        List<Category> userPersonalCategories = categoryRepository.findByCategoryTypeAndEmployee_EmpId(1, empId);
        
        // 두 리스트를 합침
        List<Category> combinedList = new ArrayList<>(defaultPersonalCategories);
        combinedList.addAll(userPersonalCategories);
        
        // DTO로 변환
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for(Category c : combinedList) {
            CategoryDto dto = new CategoryDto().toDto(c);
            categoryDtoList.add(dto);
        }
        return categoryDtoList;
	}

	public CategoryDto selectCategoryOne(Long category_no) {
		Category category = categoryRepository.findByCategoryNo(category_no);
		
		CategoryDto dto = CategoryDto.builder()
				.category_no(category.getCategoryNo())
				.category_name(category.getCategoryName())
				.category_color(category.getCategoryColor())
				.is_default(category.getIsDefault())
				.build();
		return dto;
	}

	public Category createCategory(CategoryDto dto) {
		// emp_id를 통해 Employee 엔티티를 조회
	    Employee employee = employeeRepository.findByempId(dto.getEmp_id());
	    if (employee == null) {
	        throw new IllegalArgumentException("Invalid emp_id: " + dto.getEmp_id());
	    }
	    
	    // Category 객체 생성 및 설정
	    Category category = Category.builder()
	            .categoryName(dto.getCategory_name())
	            .categoryColor(dto.getCategory_color())
	            .categoryType(dto.getCategory_type())
	            .isDefault(dto.getIs_default())
	            .employee(employee)
	            .build();

	    // Repository를 통해 Category 저장
	    return categoryRepository.save(category);
	}

	public int deleteCategory(Long category_no) {
		int result = 0;
		try {
			categoryRepository.deleteById(category_no);
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Object updateCategory(CategoryDto dto) {
		CategoryDto temp = selectCategoryOne(dto.getCategory_no());
		
		temp.setCategory_name(dto.getCategory_name());
		temp.setCategory_color(dto.getCategory_color());
		temp.setCategory_type(dto.getCategory_type());
		temp.setEmp_id(dto.getEmp_id());
		
		
		Category category = temp.toEntity(employeeRepository);
		Category result = categoryRepository.save(category);
		return result;
	}
	
	
}
