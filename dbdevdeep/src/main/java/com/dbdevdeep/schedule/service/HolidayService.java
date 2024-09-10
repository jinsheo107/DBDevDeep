package com.dbdevdeep.schedule.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbdevdeep.schedule.domain.Holiday;
import com.dbdevdeep.schedule.domain.HolidayDto;
import com.dbdevdeep.schedule.repository.HolidayRepository;

@Service
public class HolidayService {
	
	private final HolidayRepository holidayRepository;
	
	@Autowired
	public HolidayService(HolidayRepository holidayRepository) {
		this.holidayRepository = holidayRepository;
	}

	public List<HolidayDto> selectHolidayList(HolidayDto holidaydto) {
		List<Holiday> holidayList = holidayRepository.findAll();
		
		List<HolidayDto> holidayDtoList = new ArrayList<HolidayDto>();
		for(Holiday h : holidayList) {
			HolidayDto dto = new HolidayDto().toDto(h);
			holidayDtoList.add(dto);
		}
		
		return holidayDtoList;
	}

	public Holiday createHoliday(HolidayDto dto) {
		Holiday holiday = Holiday.builder()
				.holidayName(dto.getHoliday_name())
				.isPeriod(dto.getIs_period())
				.startDate(dto.getStart_date())
				.endDate(dto.getEnd_date())
				.repeatType(dto.getRepeat_type())
				.build();
		
		Holiday result = holidayRepository.save(holiday);
		
		return result;
	}

	public int deleteHoliday(Long holiday_no) {
		int result = 0;
		try {
			holidayRepository.deleteById(holiday_no);
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public HolidayDto selectHolidayOne(Long holiday_no) {
		Holiday holiday = holidayRepository.findByHolidayNo(holiday_no);
		
		HolidayDto dto = HolidayDto.builder()
				.holiday_no(holiday.getHolidayNo())
				.holiday_name(holiday.getHolidayName())
				.is_period(holiday.getIsPeriod())
				.start_date(holiday.getStartDate())
				.end_date(holiday.getEndDate())
				.repeat_type(holiday.getRepeatType())
				.reg_time(holiday.getRegTime())
				.mod_time(holiday.getModTime())
				.build();
		return dto;
	}

	public Holiday updateHoliday(HolidayDto dto) {
		HolidayDto temp = selectHolidayOne(dto.getHoliday_no());
		
		temp.setHoliday_name(dto.getHoliday_name());
		temp.setIs_period(dto.getIs_period());
		temp.setStart_date(dto.getStart_date());
		temp.setEnd_date(dto.getEnd_date());
		temp.setRepeat_type(dto.getRepeat_type());
		temp.setMod_time(LocalDateTime.now());
		
		Holiday holiday = temp.toEntity();
		Holiday result = holidayRepository.save(holiday);
		return result;
	}


}