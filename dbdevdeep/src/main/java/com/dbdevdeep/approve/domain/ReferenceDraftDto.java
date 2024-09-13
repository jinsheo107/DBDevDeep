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
public class ReferenceDraftDto {
	
	private Long ref_draft_no;
	private Long draft_no;
	private String emp_id;
	private String ref_draft_name;
	
	public ReferenceDraft toEntity() {
		return ReferenceDraft.builder()
				.refDraftNo(ref_draft_no)
				.refDraftName(ref_draft_name)
				.build();
	}
	
	public ReferenceDraftDto toDto(ReferenceDraft referenceDraft) {
		return ReferenceDraftDto.builder()
				.ref_draft_no(referenceDraft.getRefDraftNo())
				.draft_no(referenceDraft.getApproDraft().getDraftNo())
				.emp_id(referenceDraft.getEmployee().getEmpId())
				.ref_draft_name(referenceDraft.getRefDraftName())
				.build();
	}

}
