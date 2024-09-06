package com.dbdevdeep.approve.domain;

import java.time.LocalDateTime;

import com.dbdevdeep.employee.domain.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="approve_line")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class ApproveLine {
	@Id
    @Column(name = "appro_line_no")
    private Long approLineNo;

    @ManyToOne
    @JoinColumn(name = "appro_no")
    private Approve approve;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;

    @Column(name = "appro_line_order")
    private int approLineOrder;

    @Column(name = "appro_line_status")
    private int approLineStatus;

    @Column(name = "appro_permit_time")
    private LocalDateTime approPermitTime;

    @Column(name = "reason_back")
    private String reasonBack;

    @Column(name = "consult_yn")
    private String consultYn;
}
