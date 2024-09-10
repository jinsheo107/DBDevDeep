package com.dbdevdeep.employee.domain;

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
public class MySignDto {

	private Long sign_no;

	private String emp_id;
	private String emp_name;

	private String sign_type;
	private String ori_pic_name;
	private String new_pic_name;
	private String sign_title;
	private LocalDateTime reg_time;
	private LocalDateTime mod_time;
	private String rep_yn;
	
	public MySign toEntity() {
		return MySign.builder()
				.signNo(sign_no)
				.signType(sign_type)
				.oriPicName(ori_pic_name)
				.newPicName(new_pic_name)
				.signTitle(sign_title)
				.regTime(reg_time)
				.modTime(mod_time)
				.repYn(rep_yn)
				.build();
	}
	
	public MySignDto toDto(MySign m) {
		return MySignDto.builder()
				.sign_no(m.getSignNo())
				.emp_id(m.getEmployee().getEmpId())
				.emp_name(m.getEmployee().getEmpName())
				.sign_type(m.getSignType())
				.ori_pic_name(m.getOriPicName())
				.new_pic_name(m.getNewPicName())
				.sign_title(m.getSignTitle())
				.reg_time(m.getRegTime())
				.mod_time(m.getModTime())
				.rep_yn(m.getRepYn())
				.build();
	}
}

