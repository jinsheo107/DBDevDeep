package com.dbdevdeep.approve.domain;

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
@Table(name="reference_draft")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class ReferenceDraft {
	
	@Id
    @Column(name = "ref_draft_no")
    private Long refDraftNo;

    @ManyToOne
    @JoinColumn(name = "draft_no")
    private ApproDraft approDraft;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;

}
