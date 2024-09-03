package com.dbdevdeep.notice.domain;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class NoticeCategoryDto {
	
	private Long category_no;
	private String category_name;
	
	public NoticeCategory toEntity() {
		return NoticeCategory.builder()
				.categoryNo(category_no)
				.categoryName(category_name)
				.build();
	}
	
	public NoticeCategoryDto toDto(NoticeCategory nc) {
		return NoticeCategoryDto.builder()
				.category_no(nc.getCategoryNo())
				.category_name(nc.getCategoryName())
				.build();
	}
	
}
