package com.dbdevdeep.approve.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.dbdevdeep.employee.domain.Department;
import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.domain.Job;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "approve")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class Approve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appro_no")
    private Long approNo;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;

    @ManyToOne(optional = true)
    @JoinColumn(name = "temp_no" , nullable = true)
    private TempEdit tempEdit;

    @ManyToOne
    @JoinColumn(name = "dept_code")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "job_code")
    private Job job;
    
    @Column(name = "appro_name")
    private String approName;

    @Column(name = "appro_time")
    private LocalDateTime approTime;

    @Column(name = "appro_type")
    private int approType;

    @Column(name = "appro_status")
    private int approStatus;

    @Column(name = "appro_title")
    private String approTitle;

    @Column(name = "appro_content")
    private String approContent;
    
    @OneToMany(mappedBy = "approve")
    private List<ApproFile> approveFiles;

    @OneToMany(mappedBy = "approve")
    private List<VacationRequest> vacationRequests;

    @OneToMany(mappedBy = "approve")
    private List<ApproveLine> approveLines;

    @OneToMany(mappedBy = "approve")
    private List<Reference> references;

}

