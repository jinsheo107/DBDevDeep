package com.dbdevdeep.approve.domain;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="approve")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Approve {

	@Id
	@Column(name="appro_no")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long approNo;
	
//	@ManyToOne
//    @JoinColumn(name = "emp_id", nullable = false)
//    private String empId;
//
//    @ManyToOne
//    @JoinColumn(name = "temp_no")
//    private int tempNo;

	@Column(name="emp_id")
	private String empId;
	
	@Column(name="temp_no")
	private int tempNo;
	
	
    @Column(name = "appro_date", nullable = false)
    private LocalDateTime approDate;

    @Column(name = "appro_type", nullable = false)
    private Integer approType;

    @Column(name = "appro_status", nullable = false)
    private Integer approStatus;

    @Column(name = "appro_name", nullable = false)
    private String approName;

    @Column(name = "appro_context", nullable = false)
    private String approContext;

    @OneToMany(mappedBy = "approve")
    private List<ApproFile> approveFiles;

    @OneToMany(mappedBy = "approve")
    private List<VacationRequest> vacationRequests;

    @OneToMany(mappedBy = "approve")
    private List<ApproveLine> approveLines;

    @OneToMany(mappedBy = "approve")
    private List<Reference> references;

}