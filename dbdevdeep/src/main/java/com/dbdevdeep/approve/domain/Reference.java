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
@Table(name="reference")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class Reference {

	@Id
    @Column(name = "ref_no")
    private Long refNo;

    @ManyToOne
    @JoinColumn(name = "appro_no")
    private Approve approve;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;
}
