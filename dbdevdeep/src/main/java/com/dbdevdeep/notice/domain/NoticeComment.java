package com.dbdevdeep.notice.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dbdevdeep.employee.domain.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="employee")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class NoticeComment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cmt_no")
	private Long cmtNo;
	
	@ManyToOne
	@JoinColumn(name="notice_no")
	private Notice notice;
	
	@ManyToOne
	@JoinColumn(name="writer_id")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name="parent_cmt_id")
	private NoticeComment parentComment;

    @OneToMany(mappedBy = "parentComment")
    private List<NoticeComment> childComments = new ArrayList<>();
	
    @Column(name="cmt_content")
    private String cmtContent;
    
	@Column(name="reg_date")
	private LocalDateTime regDate;
	
	@Column(name="mod_date")
	private LocalDateTime modDate;
	
	
}
