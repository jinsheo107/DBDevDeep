package com.dbdevdeep.schedule.domain;

import java.time.LocalDateTime;
import java.util.Date;

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
    
    @Column(name = "start_time")
    private LocalDateTime startTIme;
    
    @Column(name = "end_time")
    private LocalDateTime endTime;
    
    @Column(name = "is_all_day")
    private String isAllDay;
    
    @Column(name = "schedule_place")
    private String schedulePlace;
    
    @Column(name = "recurrence")
    private String recurrence;
    
    @Column(name = "repeat_start_date")
    private Date repeatStartDate;
    
    @Column(name = "repeat_end_date")
    private Date repeatEndDate;
    
    @Column(name = "reg_time")
    @CreationTimestamp
    private LocalDateTime regTime;
    
    @Column(name = "mod_time")
    @UpdateTimestamp
    private LocalDateTime modTime;
    
    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;
    
    @ManyToOne
    @JoinColumn(name = "category_no")
    private Category category;
    
}
