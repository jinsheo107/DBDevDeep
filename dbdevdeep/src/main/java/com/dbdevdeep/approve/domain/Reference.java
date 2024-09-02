package com.dbdevdeep.approve.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="reference")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Reference {

	@Id
	@Column(name="ref_no")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long refNo;
	
	@Column(name = "appro_no")
	private Long approNo;
	
	@Column(name = "emp_id")
	private String empId;
}
