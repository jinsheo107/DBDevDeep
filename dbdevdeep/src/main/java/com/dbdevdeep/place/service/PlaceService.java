package com.dbdevdeep.place.service;

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
	
	// 사용 불가 시작일 가공 데이터
	public List<String> getFormattedUnuseableStartDate() {
        return placeRepository.findFormattedUnuseableStartDate();
    }
	
	// 사용 불가 종료일 가공 데이터 가져오기
    public List<String> getFormattedUnuseableEndDate() {
        return placeRepository.findFormattedUnuseableEndDate();
    }
	
	//목록
	public List<PlaceDto> selectPlaceList(PlaceDto placeDto){
		List<Place> placeList = placeRepository.findAll();
		
		List<PlaceDto> placeDtoList = new ArrayList<PlaceDto>();
		for(Place p : placeList) {
			PlaceDto dto = new PlaceDto().toDto(p);
			placeDtoList.add(dto);
			
		}
		return placeDtoList;
	}

}
