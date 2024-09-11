package com.dbdevdeep.approve.domain;

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
@Table(name="line_draft")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class LineDraft {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "draft_line_no")
    private Long draftLineNo;

    @ManyToOne
    @JoinColumn(name = "draft_no")
    private ApproDraft approDraft;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;
    
    @Column(name = "draft_line_name")
    private String draftLineName;

    @Column(name = "line_order")
    private int lineOrder;

    @Column(name = "consult_yn")
    private String consultYn;
}
