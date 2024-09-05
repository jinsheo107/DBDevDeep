package com.dbdevdeep.employee.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dbdevdeep.employee.vo.GradeClassGroup;

@Mapper
public interface TeacherHistoryVoMapper {

	List<GradeClassGroup> selectGroupByYear();
}
