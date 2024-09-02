package com.dbdevdeep.approve.domain;

import java.time.LocalDateTime;

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
@Table(name="approve_line")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class ApproveLine {

	@Id
	@Column(name = "appro_line_no")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long approLineNo;

    @Column(name = "appro_no")
    private Long approNo;

    @Column(name = "emp_id")
    private String empId;

    @Column(name = "appro_line_order")
    private Integer approLineOrder;

    @Column(name = "appro_line_status")
    private Integer approLineStatus;

    @Column(name = "appro_line_date")
    private LocalDateTime approLineDate;

    @Column(name = "appro_back")
    private String approBack;

    @Column(name = "consult_yn")
    private String consultYn;
}
