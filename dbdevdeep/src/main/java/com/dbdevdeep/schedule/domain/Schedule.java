package com.dbdevdeep.schedule.domain;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
@Table(name = "schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Schedule {
	
    @Id
    @Column(name = "schedule_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleNo;
    
    @Column(name = "calendar_type")
    private int calendarType;
    
    @Column(name = "schedule_title")
    private String scheduleTitle;
    
    @Column(name = "schedule_content")
    private String scheduleContent;
    
    @Column(name = "start_date")
    private LocalDate startDate;
    
    @Column(name = "start_time")
    private LocalTime startTime;
    
    @Column(name = "end_date")
    private LocalDate endDate;
    
    @Column(name = "end_time")
    private LocalTime endTime;
    
    @Column(name = "is_all_day")
    private String isAllDay;
    
    @Column(name = "schedule_place")
    private String schedulePlace;
    
    @Column(name = "repeat_type")
    private String repeatType;
    
    @Column(name = "repeat_start_date")
    private LocalDate repeatStartDate;
    
    @Column(name = "repeat_end_date")
    private LocalDate repeatEndDate;
    
    @Column(name = "reg_time")
    @CreationTimestamp
    private LocalDateTime regTime;
    
    @Column(name = "mod_time")
    @UpdateTimestamp
    private LocalDateTime modTime;
    
    @Column(name = "alert_type")
    private String alertType;
    
    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;
    
    @ManyToOne
    @JoinColumn(name = "category_no")
    private Category category;
    
}
