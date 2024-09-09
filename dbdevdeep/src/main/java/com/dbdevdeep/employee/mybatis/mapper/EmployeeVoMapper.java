package com.dbdevdeep.employee.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dbdevdeep.employee.vo.EmployeeVo;

@Mapper
public interface EmployeeVoMapper {
	int updateLoginYn(EmployeeVo vo);

	List<EmployeeVo> selectEmployeeByNotTeacher(String t_year);
}
