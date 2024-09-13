package com.dbdevdeep.approve.domain;

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
public class LineDraftDto {

	private Long draft_line_no;
	private Long draft_no;
	private String emp_id;
	private String draft_line_name;
	private int line_order;
	private String consult_yn;
	
	public LineDraft toEntity() {
		return LineDraft.builder()
				.draftLineNo(draft_line_no)
				.draftLineName(draft_line_name)
				.lineOrder(line_order)
				.consultYn(consult_yn)
				.build();
	}
	
	public LineDraftDto toDto(LineDraft lineDraft) {
		return LineDraftDto.builder()
				.draft_line_no(lineDraft.getDraftLineNo())
				.draft_no(lineDraft.getApproDraft().getDraftNo())
				.emp_id(lineDraft.getEmployee().getEmpId())
				.draft_line_name(lineDraft.getDraftLineName())
				.line_order(lineDraft.getLineOrder())
				.consult_yn(lineDraft.getConsultYn())
				.build();
	}
}
