package com.dbdevdeep.approve.domain;


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
@Table(name = "appro_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class ApproFile {

    @Id
    @Column(name = "file_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileNo;

    @ManyToOne
    @JoinColumn(name = "appro_no", nullable = false) // 외래 키 설정
    private Approve approve;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "new_file_name")
    private String newFileName;

    @Column(name = "appro_root")
    private String approRoot;

}
