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
public class ApproFileDto {

	private Long file_no;
	private Long appro_no;
	private String ori_file;
	private String new_file;
	private String appro_root;
	
	public ApproFile toEntity() {
		return ApproFile.builder()
				.fileNo(file_no)
				.oriFile(ori_file)
				.newFile(new_file)
				.approRoot(appro_root)
				.build();
	}
	
	public ApproFileDto toDto(ApproFile approFile) {
		return ApproFileDto.builder()
				.file_no(approFile.getFileNo())
				.appro_no(approFile.getApprove().getApproNo())
				.ori_file(approFile.getOriFile())
				.new_file(approFile.getNewFile())
				.appro_root(approFile.getApproRoot())
				.build();
	}
}
