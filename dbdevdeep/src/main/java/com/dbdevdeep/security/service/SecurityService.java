package com.dbdevdeep.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.domain.EmployeeDto;
import com.dbdevdeep.employee.mybatis.mapper.EmployeeVoMapper;
import com.dbdevdeep.employee.repository.EmployeeRepository;
import com.dbdevdeep.employee.vo.EmployeeVo;
import com.dbdevdeep.security.vo.SecurityUser;


@Service
public class SecurityService implements UserDetailsService {

	private final EmployeeRepository employeeRepository;
	private final EmployeeVoMapper employeeVoMapper;
	
	@Autowired
	public SecurityService(EmployeeRepository employeeRepository,
			EmployeeVoMapper employeeVoMapper) {
		this.employeeRepository = employeeRepository;
		this.employeeVoMapper = employeeVoMapper;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepository.findByempId(username);

		if (employee != null) {
			if("Y".equals(employee.getEntStatus())) { // 재직
				return LoginData(employee);
			} else {
				throw new UsernameNotFoundException(username);
			}
		} else {
			throw new UsernameNotFoundException(username);
		}
	}

	
	public SecurityUser LoginData(Employee employee) {
		EmployeeDto dto = new EmployeeDto().toDto(employee);
		
		EmployeeVo empVo = new EmployeeVo(dto.getEmp_id(), 
				dto.getEmp_pw(), 
				dto.getGov_id(), 
				dto.getEmp_name(), 
				dto.getEmp_rrn(), 
				dto.getEmp_phone(),
				dto.getOri_pic_name(), 
				dto.getNew_pic_name(), 
				dto.getEmp_post_code(), 
				dto.getEmp_addr(),
				dto.getEmp_detail_addr(), 
				dto.getDept_code(), 
				dto.getJob_code(), 
				dto.getEmp_internal_phone(), 
				dto.getVacation_hour(), 
				dto.getHire_date(),
				dto.getEnd_date(), 
				dto.getEnt_status(), 
				"Y",
				dto.getAccount_status(), 
				dto.getChat_status_msg());
		

		// login_yn값 db에 반영
		int resultInt = employeeVoMapper.updateLoginYn(empVo);
		
		if(resultInt > 0) {
			Employee e = employeeRepository.findByempId(employee.getEmpId());
			EmployeeDto d = new EmployeeDto().toDto(e);
			
			// authorities 설정
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

			// 부서 기준 권한 설정
			authorities.add(new SimpleGrantedAuthority(employee.getDepartment().getDeptCode()));

			// 직위 기준 권한 설정
			authorities.add(new SimpleGrantedAuthority(employee.getJob().getJobCode()));
			
			// 재직 기준 권한 설정
			authorities.add(new SimpleGrantedAuthority(employee.getEntStatus()));

			d.setAuthorities(authorities);
			
			return new SecurityUser(d);
			
		} else {
			throw new UsernameNotFoundException(employee.getEmpId());
		}
	}

}
