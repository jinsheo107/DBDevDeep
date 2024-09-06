package com.dbdevdeep.place.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbdevdeep.place.domain.Place;
import com.dbdevdeep.place.domain.PlaceDto;
import com.dbdevdeep.place.repository.PlaceRepository;

@Service
public class PlaceService {
	
	private final PlaceRepository placeRepository;
	
	@Autowired
	public PlaceService(PlaceRepository placeRepository) {
		this.placeRepository = placeRepository;
	}
	
	// 시간을 포맷팅하는 곳
	public String formatTime(int time) {
		// 인트타입 한자리수를, :00 형태로 바꿔주는것
		return String.format("%02d:00", time);
	}
	
	// 시간 범위를 포맷팅해서 변환하는 곳
	public String getFormattedTimeRange(Place place) {
		String startTime = formatTime(place.getPlaceStarttime());
		String endTime = formatTime(place.getPlaceEndtime());
		return startTime + " - " + endTime;
	}
	
	
	
	// 사용 불가 시작일 가공 데이터
	public List<String> getFormattedUnuseableStartDate() {
		List<String> dates = placeRepository.findFormattedUnuseableStartDate();
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		
		List<String> formattedDates = new ArrayList<>();
		for(String date : dates) {
			LocalDate parsedDate = LocalDate.parse(date, inputFormatter);
			formattedDates.add(parsedDate.format(outputFormatter));
		}
		return formattedDates;
        // return placeRepository.findFormattedUnuseableStartDate();
    }
	
	// 사용 불가 종료일 가공 데이터 가져오기
    public List<String> getFormattedUnuseableEndDate() {
       List<String> dates = placeRepository.findFormattedUnuseableEndDate();
       DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
       DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		
		List<String> formattedDates = new ArrayList<>();
		for(String date : dates) {
			LocalDate parsedDate = LocalDate.parse(date, inputFormatter);
			formattedDates.add(parsedDate.format(outputFormatter));
		}
		return formattedDates;
    }
	
	//장소 목록
	public List<PlaceDto> selectPlaceList(PlaceDto placeDto){
		List<Place> placeList = placeRepository.findAll();
		List<PlaceDto> placeDtoList = new ArrayList<PlaceDto>();
		
		// 날짜 형태 폼 변환
		  DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		   DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		    
		// Place 엔티티를 PlaceDto로 변환
		for(Place p : placeList) {
			PlaceDto dto = new PlaceDto().toDto(p);
			
			// 시간 데이터 포맷 시간범위

			
			// 날짜 데이터를 포맷해서 DTO에 추가
			if(p.getUnuseableStartDate() != null) {
				LocalDate startDate = LocalDate.parse(p.getUnuseableStartDate(), inputFormatter);
				dto.setUnuseable_start_date(startDate.format(outputFormatter));
				
			}
			
			if(p.getUnuseableEndDate() != null) {
				LocalDate endDate = LocalDate.parse(p.getUnuseableEndDate(), inputFormatter);
				dto.setUnuseable_end_date(endDate.format(outputFormatter));
			}
			
			placeDtoList.add(dto);
			
		}
		return placeDtoList;
	}

}
