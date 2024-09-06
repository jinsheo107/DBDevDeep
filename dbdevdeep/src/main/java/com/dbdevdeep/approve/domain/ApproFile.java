package com.dbdevdeep.approve.domain;

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
@Table(name="appro_file")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class ApproFile {
	
	@Id
    @Column(name = "file_no")
    private Long fileNo;

    @ManyToOne
    @JoinColumn(name = "appro_no")
    private Approve approve;

    @Column(name = "ori_file")
    private String oriFile;

    @Column(name = "new_file")
    private String newFile;

    @Column(name = "appro_root")
    private String approRoot;
	
}
