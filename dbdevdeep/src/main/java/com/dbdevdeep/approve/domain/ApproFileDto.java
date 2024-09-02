package com.dbdevdeep.approve.domain;

import java.time.LocalDateTime;

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
	private String file_name;
	private String new_file_name;
	private String appro_root;
	
	public ApproFile toEntity() {
		return ApproFile.builder()
				.fileNo(file_no)
				.approNo(appro_no)
				.fileName(file_name)
				.newFileName(new_file_name)
				.approRoot(appro_root)
				.build();
				
	}
	
	public ApproFileDto toDto(ApproFile approFile) {
		return ApproFileDto.builder()
				.file_no(approFile.getFileNo())
				.appro_no(approFile.getApproNo())
				.file_name(approFile.getFileName())
				.new_file_name(approFile.getNewFileName())
				.appro_root(approFile.getApproRoot())
				.build();
	}
}
