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
	private Employee emp_id;
	private String place_name;
	private String place_location;
	private String place_content;
	private String place_status;
	private int place_start_time;
	private int place_end_time;
	private String unuseable_reason;
	private String unuseable_start_date;
	private String unuseable_end_date;
	private String ori_pic_name;
	private String new_pic_name;
	private LocalDateTime reg_date;
	private LocalDateTime mod_date;

	
	public Place toEntity() {
		return Place.builder()
				.placeNo(place_no)
				.employee(emp_id)
				.placeName(place_name)
				.placeLocation(place_location)
				.placeContent(place_content)
				.placeStatus(place_status)
				.placeStarttime(place_start_time)
				.placeEndtime(place_end_time)
				.unuseableReason(unuseable_reason)
				.unuseableStartDate(unuseable_start_date)
				.unuseableEndDate(unuseable_end_date)
				.oriPicname(ori_pic_name)
				.newPicname(new_pic_name)
				.regDate(reg_date)
				.modDate(mod_date)
				.build();
	}
	
	public PlaceDto toDto (Place place) {
		return PlaceDto.builder()
				.place_no(place.getPlaceNo())
				.emp_id(place.getEmployee())
				.place_name(place.getPlaceName())
				.place_location(place.getPlaceLocation())
				.place_content(place.getPlaceContent())
				.place_status(place.getPlaceStatus())
				.place_start_time(place.getPlaceStarttime())
				.place_end_time(place.getPlaceEndtime())
				.unuseable_reason(place.getUnuseableReason())
				.unuseable_start_date(place.getUnuseableStartDate())
				.unuseable_end_date(place.getUnuseableEndDate())
				.ori_pic_name(place.getOriPicname())
				.new_pic_name(place.getNewPicname())
				.reg_date(place.getRegDate())
				.mod_date(place.getModDate())
				.build();
				
	}
	
}
