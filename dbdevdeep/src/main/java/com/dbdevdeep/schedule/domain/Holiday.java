package com.dbdevdeep.schedule.domain;

import java.util.Date;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "holiday")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Holiday {

    @Id
    @Column(name = "holiday_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long holidayNo;

    @Column(name = "holiday_name")
    private String holidayName;

    @Column(name = "is_period")
    private String isPeriod;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "repeat_type")
    private String repeatType;

    @Column(name = "reg_time")
    @CreationTimestamp
    private LocalDateTime regTime;

    @Column(name = "mod_time")
    @UpdateTimestamp
    private LocalDateTime modTime;
}