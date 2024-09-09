package com.dbdevdeep.schedule.domain;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class HolidayDto {
	
    private Long holiday_no;
    private String holiday_name;
    private String is_period;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date start_date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date end_date;
    
    private String repeat_type;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime reg_time;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime mod_time;

    public Holiday toEntity() {
        return Holiday.builder()
                .holidayNo(holiday_no)
                .holidayName(holiday_name)
                .isPeriod(is_period)
                .startDate(start_date)
                .endDate(end_date)
                .repeatType(repeat_type)
                .regTime(reg_time)
                .modTime(mod_time)
                .build();
    }
    
    public HolidayDto toDto(Holiday holiday) {
    	return HolidayDto.builder()
    			.holiday_no(holiday.getHolidayNo())
    			.holiday_name(holiday.getHolidayName())
    			.is_period(holiday.getIsPeriod())
    			.start_date(holiday.getStartDate())
    			.end_date(holiday.getEndDate())
    			.repeat_type(holiday.getRepeatType())
    			.reg_time(holiday.getRegTime())
    			.mod_time(holiday.getModTime())
    			.build();
    }
}