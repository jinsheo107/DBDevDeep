package com.dbdevdeep.place.domain;

import java.time.LocalDateTime;

import com.dbdevdeep.employee.domain.Employee;

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
public class PlaceDto {

	private Long place_no;
	private String place_name;
	private LocalDateTime place_created_at;
	private LocalDateTime place_updated_at;
	private String place_location;
	private Employee emp_id;
	private String place_id;
	private String place_content;
	private String place_status;
	private String place_usetime;
	private String place_unuseable_reason;
	private LocalDateTime place_unuseable_start_date;
	private LocalDateTime place_unuseable_end_date;
	
	public Place toEntity() {
		return Place.builder()
				.placeNo(place_no)
				.placeName(place_name)
				.placeCreatedAt(place_created_at)
				.placeUpdatedAt(place_updated_at)
				.placeLocation(place_location)
				.employee(emp_id)
				.placeId(place_id)
				.placeContent(place_content)
				.placeStatus(place_status)
				.placeUsetime(place_usetime)
				.placeUnuseableReason(place_unuseable_reason)
				.placeUnuseableStartDate(place_unuseable_start_date)
				.placeUnuseableEndDate(place_unuseable_end_date)
				.build();
	}
	
}
