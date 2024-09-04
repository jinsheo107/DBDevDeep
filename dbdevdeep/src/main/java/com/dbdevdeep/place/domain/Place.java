package com.dbdevdeep.place.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.dbdevdeep.employee.domain.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="place")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class Place {
	// 장소 번호(관리번호로 사용될 애)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "place_no")
	private Long placeNo;
	// 장소 이름
	@Column(name = "place_name")
	private String placeName;
	// 직원번호(참조)
	@ManyToOne
	@JoinColumn(name = "emp_id")
	private Employee employee;
	// 장소위치
	@Column(name = "place_location")
	private String placeLocation;
	// 장소내용(설명)
	@Column(name = "place_content")
	private String placeContent;
	// 장소 가능여부
	@Column(name = "place_status")
	private String placeStatus;
	// 사용가능 시작시간
	@Column(name = "place_start_time")
	private String placeStarttime;
	// 사용가능 종료시간
	@Column(name = "place_end_time")
	private String placeEndtime;
	// 등록날짜
	@Column(name = "place_created_at")
	@CreationTimestamp
	private LocalDateTime placeCreatedAt;
	// 수정날짜
	@Column(name = "place_updated_at")
	@UpdateTimestamp
	private LocalDateTime placeUpdatedAt;
	// 사용불가 사유
	@Column(name = "place_unuseable_reason")
	private String placeUnuseableReason;
	// 사용불가 시작일
	@Column(name = "place_unuseable_start_date")
	private LocalDateTime placeUnuseableStartDate;
	// 사용불가 종료일
	@Column(name = "place_unuseable_end_date")
	private LocalDateTime placeUnuseableEndDate;
	
}
