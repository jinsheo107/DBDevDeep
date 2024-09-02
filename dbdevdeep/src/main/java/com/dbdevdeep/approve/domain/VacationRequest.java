package com.dbdevdeep.approve.domain;

import java.time.LocalDate;

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
@Table(name="vacation_request")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class VacationRequest {

	@Id
	@Column(name="vac_no")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long vacNo;
	
	@Column(name="appro_no")
	private Long approNo;
	
	@Column(name="vac_yn")
	private String vacYn;
	
	@Column(name="vac_type")
	private int vacType;
	
	@Column(name="start_date")
	private LocalDate startDate;
	
	@Column(name="end_date")
	private LocalDate endDate;
}
