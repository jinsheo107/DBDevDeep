package com.dbdevdeep.approve.domain;

import java.time.LocalDateTime;

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
@Table(name="vacation_request")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class VacationRequest {

	@Id
    @Column(name = "vac_no")
    private Long vacNo;

    @ManyToOne
    @JoinColumn(name = "appro_no")
    private Approve approve;

    @Column(name = "vac_yn", nullable = false)
    private String vacYn;

    @Column(name = "vac_type", nullable = false)
    private int vacType;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;
}
