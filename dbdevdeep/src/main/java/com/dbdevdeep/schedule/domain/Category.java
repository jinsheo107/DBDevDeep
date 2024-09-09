package com.dbdevdeep.schedule.domain;

import java.util.List;

import com.dbdevdeep.employee.domain.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Category {
	
	@Id
	@Column(name = "category_no")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryNo;
	
	@Column(name = "category_type")
	private int categoryType;
	
	@Column(name = "category_name")
	private String categoryName;
	
	@Column(name = "category_color")
	private String categoryColor;
	
	@Column(name = "is_default")
	private String isDefault;
	
	@OneToMany(mappedBy = "category")
	private List<Schedule> schedules;
	
	@ManyToOne()
	@JoinColumn(name="emp_id")
	private Employee employee;
	
}
