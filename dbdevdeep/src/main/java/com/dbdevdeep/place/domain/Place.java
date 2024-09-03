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
	// 장소 번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "place_no")
	private int placeNo;
	// 장소 이름
	@Column(name = "place_name")
	private String placeName;
	// 등록날짜
	@Column(name = "place_created_at")
	@CreationTimestamp
	private LocalDateTime placeCreatedAt;
	// 수정날짜
	@Column(name = "place_updated_at")
	@UpdateTimestamp
	private LocalDateTime placeUpdatedAt;
	// 장소위치
	@Column(name = "place_location")
	private String placeLocation;
	// 직원번호(참조)
	@ManyToOne
	@JoinColumn(name = "emp_id")
	private Employee employee;
	// 관리번호
	@Column(name = "place_id")
	private String placeId;
	// 장소내용(설명)
	@Column(name = "place_content")
	private String placeContent;
	
	@Column(name = "place_status")
	private String placeStatus;
	
	@Column(name = "palce_usetime")
	private String placeUsetime;
	
	
	
}
