package com.dbdevdeep.schedule.domain;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	private String category_type;
	private String category_name;
	private String category_color;
	private String is_default;
	
	public Category toEntity() {
		return Category.builder()
				.categoryNo(category_no)
				.categoryType(category_type)
				.categoryName(category_name)
				.categoryColor(category_color)
				.isDefault(is_default)
				.build();
	}
	
	public CategoryDto toDto(Category category) {
		return CategoryDto.builder()
				.category_no(category.getCategoryNo())
				.category_type(category.getCategoryType())
				.category_name(category.getCategoryName())
				.category_color(category.getCategoryColor())
				.is_default(category.getIsDefault())
				.build();
	}
	
}
