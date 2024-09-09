package com.dbdevdeep.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbdevdeep.schedule.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	Category findByCategoryNo(Long category_no);

	List<Category> findByCategoryType(int categoryType);
	
	List<Category> findByCategoryTypeAndEmployee_EmpId(int categoryType, String empId);

	List<Category> findByCategoryTypeAndIsDefault(int categoryType, String string); 
}
