package com.dbdevdeep.security.vo;

import org.springframework.security.core.userdetails.User;

import com.dbdevdeep.employee.domain.EmployeeDto;

import lombok.Getter;

@Getter
public class SecurityUser extends User{

	private EmployeeDto dto;
	
	public SecurityUser(EmployeeDto dto) {
		super(dto.getEmp_id(), dto.getEmp_pw(), dto.getAuthorities());
		this.dto = dto;
	}
}
