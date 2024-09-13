package com.dbdevdeep.approve.domain;

import java.time.LocalDateTime;

import com.dbdevdeep.employee.domain.Employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ApproveLineDto {

    private Long appro_line_no;
    private Long appro_no;
    private String emp_id;
    private String appro_line_name;
    private int appro_line_order;
    private int appro_line_status;
    private LocalDateTime appro_permit_time;
    private String reason_back;
    private String consult_yn;

    public ApproveLine toEntity(Approve approve, Employee employee) {
        if (employee == null) {
            return null; // 이 경우, 적절한 처리 (예: 예외 던지기)도 고려할 수 있습니다.
        }

        return ApproveLine.builder()
                .approLineNo(appro_line_no)
                .approve(approve)
                .employee(employee)
                .approLineName(appro_line_name)
                .approLineOrder(appro_line_order)
                .approLineStatus(appro_line_status)
                .approPermitTime(appro_permit_time)
                .reasonBack(reason_back)
                .consultYn(consult_yn)
                .build();
    }

    public ApproveLineDto toDto(ApproveLine approveLine) {
        return ApproveLineDto.builder()
                .appro_line_no(approveLine.getApproLineNo())
                .appro_no(approveLine.getApprove().getApproNo())
                .emp_id(approveLine.getEmployee().getEmpId())
                .appro_line_name(approveLine.getApproLineName())
                .appro_line_order(approveLine.getApproLineOrder())
                .appro_line_status(approveLine.getApproLineStatus())
                .appro_permit_time(approveLine.getApproPermitTime())
                .reason_back(approveLine.getReasonBack())
                .consult_yn(approveLine.getConsultYn())
                .build();
    }
}
