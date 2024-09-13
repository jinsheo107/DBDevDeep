package com.dbdevdeep.place.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.repository.EmployeeRepository;
import com.dbdevdeep.place.domain.Place;
import com.dbdevdeep.place.domain.PlaceDto;
import com.dbdevdeep.place.repository.PlaceRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PlaceService {
	
	private final PlaceRepository placeRepository;
	private final EmployeeRepository employeeRepository;
	
	@Autowired
	public PlaceService(PlaceRepository placeRepository, EmployeeRepository employeeRepository) {
		this.placeRepository = placeRepository;
		this.employeeRepository = employeeRepository;
	}
	
	
	// 삭제하기
	public int deletePlace(Long place_no) {
		int result = -1;
		try {
			placeRepository.deleteById(place_no);
			result = 1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 수정하기
	public int updatePlace(PlaceDto dto) {
		int result = -1;
		
		// 필수 값 검증
        if (dto.getPlace_name() == null || dto.getPlace_name().isEmpty()) {
            throw new IllegalArgumentException("장소명은 필수 입력 항목입니다.");
        }
        if (dto.getPlace_status() == null || dto.getPlace_status().isEmpty()) {
            throw new IllegalArgumentException("장소 상태는 필수 입력 항목입니다.");
        }
		
		try {

	         Employee e = employeeRepository.findByempId(dto.getEmp_id());
	    		
	         // 만약 상태가 "사용가능"이라면 사용 불가 날짜를 null로 설정
	         if ("Y".equals(dto.getPlace_status())) {
	             dto.setUnuseable_start_date(null);
	             dto.setUnuseable_end_date(null);
	             dto.setUnuseable_reason(null);
	         }
	         
	    		Place p = Place.builder()
	    				.placeNo(dto.getPlace_no())
	    				.employee(e)
	    				.placeName(dto.getPlace_name())
	    				.placeLocation(dto.getPlace_location()) // 위치
	    				.placeContent(dto.getPlace_content())  // 설명이 null일 경우 처리
	                  .placeStatus(dto.getPlace_status())    // 상태
	                  .placeStarttime(dto.getPlace_start_time()) // 사용 가능 시작 시간
	                  .placeEndtime(dto.getPlace_end_time())    // 사용 가능 종료 시간
	                  .unuseableReason(dto.getUnuseable_reason()) // 사용 불가 사유
	                  .unuseableStartDate(dto.getUnuseable_start_date()) // 사용 불가 시작 날짜
	                  .unuseableEndDate(dto.getUnuseable_end_date())   // 사용 불가 종료 날짜
	                  .oriPicname(dto.getOri_pic_name() != null ? dto.getOri_pic_name() : "Default oriPicname")
	                  .newPicname(dto.getNew_pic_name() != null ? dto.getNew_pic_name() : "Default newPicname")
	                  .regDate(dto.getReg_date())        // 등록일
	                  .modDate(LocalDateTime.now())        // 수정일
	                  .build();
	    		
	    		placeRepository.save(p);
	    		result = 1;
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	//  게시글 상세조회
	public PlaceDto selectPlaceOne(Long place_no) {
		
		Place p = placeRepository.findByplaceNo(place_no);
		
		if(p == null) {
			throw new EntityNotFoundException("Place not found for place_no: " + place_no);
		}
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		
		PlaceDto dto = PlaceDto.builder()
				.place_name(p.getPlaceName())
				.place_no(p.getPlaceNo())
				.place_status(p.getPlaceStatus())
				.place_start_time(p.getPlaceStarttime())
				.place_end_time(p.getPlaceEndtime())
				.unuseable_reason(p.getUnuseableReason())
				   // 날짜 포맷 적용: null이 아닌 경우에만 포맷을 적용 ==> yyyy-MM-dd , yyyy.MM.dd
				.unuseable_start_date(p.getUnuseableStartDate())
                .unuseable_end_date(p.getUnuseableEndDate())
				.ori_pic_name(p.getOriPicname() != null ? p.getOriPicname() : "Default oriPicname")
                .new_pic_name(p.getNewPicname() != null ? p.getNewPicname() : "Default newPicname")			
				.place_content(p.getPlaceContent())
				.place_location(p.getPlaceLocation())
				.build();
		
		return dto;
	}
	
	
	
	// 로그인한 사용자 정보 가져오기
    public String getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            return user.getUsername(); // 로그인한 사용자의 emp_id를 반환
        }
        return null; // 로그인이 안 되어 있으면 null 반환
    }

 // 장소 등록
    public int createPlace(PlaceDto dto) {
    	int result = -1;
    	
        // 필수 값 검증
        if (dto.getPlace_name() == null || dto.getPlace_name().isEmpty()) {
            throw new IllegalArgumentException("장소명은 필수 입력 항목입니다.");
        }
        if (dto.getPlace_status() == null || dto.getPlace_status().isEmpty()) {
            throw new IllegalArgumentException("장소 상태는 필수 입력 항목입니다.");
        }
    	try {
    		// 가장 큰 placeNo 값을 찾아서, 없으면 0을 할당하고, 있으면 +1
          Long maxPlaceNo = placeRepository.findMaxPlaceNo();  // 가장 큰 placeNo 값 찾기
          Long nextPlaceNo = (maxPlaceNo == null) ? 0L : maxPlaceNo + 1;  // 없으면 0, 있으면 +1
    		Employee e = employeeRepository.findByempId(dto.getEmp_id());
    		
    		dto.getMod_date();
			Place p = Place.builder()
    				.placeNo(nextPlaceNo)
    				.employee(e)
    				.placeName(dto.getPlace_name())
    				.placeLocation(dto.getPlace_location()) // 위치
    				.placeContent(dto.getPlace_content())  // 설명이 null일 경우 처리
                  .placeStatus(dto.getPlace_status())    // 상태
                  .placeStarttime(dto.getPlace_start_time()) // 사용 가능 시작 시간
                  .placeEndtime(dto.getPlace_end_time())    // 사용 가능 종료 시간
                  .unuseableReason(dto.getUnuseable_reason()) // 사용 불가 사유
                  .unuseableStartDate(dto.getUnuseable_start_date()) // 사용 불가 시작 날짜
                  .unuseableEndDate(dto.getUnuseable_end_date())   // 사용 불가 종료 날짜
                  .oriPicname(dto.getOri_pic_name() != null ? dto.getOri_pic_name() : "Default oriPicname")
                  .newPicname(dto.getNew_pic_name() != null ? dto.getNew_pic_name() : "Default newPicname")
                  .regDate(dto.getReg_date())        // 등록일
                  .modDate(dto.getMod_date())        // 수정일
                  .build();
    		
    		placeRepository.save(p);
    		result = 1;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		return result;
    }
    
	
	
	// 시간을 포맷팅하는 곳
	public String formatTime(String time) {
		if(time != null && !time.isEmpty()) {
			return time;
		}
		
		return "00:00"; // 기본값
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
			String formattedTimeRange = getFormattedTimeRange(p);
			dto.setFormattedTimeRange(formattedTimeRange);
			
			// 날짜 데이터를 포맷해서 DTO에 추가
			if(p.getUnuseableStartDate() != null && !p.getUnuseableStartDate().trim().isEmpty()) {
				LocalDate startDate = LocalDate.parse(p.getUnuseableStartDate(), inputFormatter);
				dto.setUnuseable_start_date(startDate.format(outputFormatter));
				
			}
			
			if(p.getUnuseableEndDate() != null && !p.getUnuseableEndDate().trim().isEmpty()) {
				LocalDate endDate = LocalDate.parse(p.getUnuseableEndDate(), inputFormatter);
				dto.setUnuseable_end_date(endDate.format(outputFormatter));
			}
			
			placeDtoList.add(dto);
			
		}
		return placeDtoList;
	}

}
