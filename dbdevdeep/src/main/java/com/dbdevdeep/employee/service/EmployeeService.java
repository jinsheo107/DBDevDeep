package com.dbdevdeep.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dbdevdeep.employee.domain.Department;
import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.domain.EmployeeDto;
import com.dbdevdeep.employee.domain.Job;
import com.dbdevdeep.employee.mybatis.mapper.EmployeeVoMapper;
import com.dbdevdeep.employee.repository.DepartmentRepository;
import com.dbdevdeep.employee.repository.EmployeeRepository;
import com.dbdevdeep.employee.repository.JobRepository;
import com.dbdevdeep.employee.vo.EmployeeVo;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder passwordEncoder;
	private final JobRepository jobRepository;
	private final DepartmentRepository departmentRepository;
	private final EmployeeVoMapper employeeVoMapper;

	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder,
			JobRepository jobRepository, DepartmentRepository departmentRepository,
			EmployeeVoMapper employeeVoMapper) {
		this.employeeRepository = employeeRepository;
		this.passwordEncoder = passwordEncoder;
		this.jobRepository = jobRepository;
		this.departmentRepository = departmentRepository;
		this.employeeVoMapper = employeeVoMapper;
	}

	public int govIdCheck(String govId) {
		int result = -1;

		try {
			Employee e = employeeRepository.findBygovId(govId);

			if (e != null) {
				result = 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public int addEmployee(EmployeeDto dto) {
		int result = -1;
		
		String dept_code = dto.getDept_code();
		String job_code = dto.getJob_code();
		
		Department dept = departmentRepository.findByDeptCode(dept_code);
		Job job = jobRepository.findByJobCode(job_code);
		
		try {
			dto.setEmp_pw(passwordEncoder.encode(dto.getEmp_pw()));

			String currentYear = String.valueOf(dto.getHire_date()).substring(0, 4);

			int count = Integer.parseInt(employeeRepository.findByempIdWhen(currentYear));
			
			int empYearCount = count + 1;
			String emp_id = "";
			
			if(empYearCount < 10) {
				emp_id = currentYear + "00" + empYearCount;
			} else if(empYearCount < 100) {
				emp_id = currentYear + "0" + empYearCount;
			} else {
				emp_id = currentYear + empYearCount;
			}
			dto.setEmp_id(emp_id);
			
			dto.setAccount_status("Y");
			dto.setLogin_yn("N");
			dto.setEnt_status("Y");
			dto.setVacation_hour(120);

			Employee e = Employee.builder()
					.empId(emp_id).empPw(dto.getEmp_pw()).govId(dto.getGov_id())
					.empName(dto.getEmp_name()).empRrn(dto.getEmp_rrn()).empPhone(dto.getEmp_phone())
					.oriPicName(dto.getOri_pic_name()).newPicName(dto.getNew_pic_name()).empPostCode(dto.getEmp_post_code())
					.empAddr(dto.getEmp_addr()).empDetailAddr(dto.getEmp_detail_addr())
					.empInternalPhone(dto.getEmp_internal_phone()).vacationHour(dto.getVacation_hour())
					.hireDate(dto.getHire_date()).endDate(dto.getEnd_date()).entStatus(dto.getEnt_status())
					.loginYn(dto.getLogin_yn()).accountStatus(dto.getAccount_status())
					.chatStatusMsg(dto.getChat_status_msg())
					.job(job).department(dept).build();
			
			employeeRepository.save(e);
			
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public List<EmployeeDto> selectYEmployeeList() {
		List<Employee> employeeList = employeeRepository.selectYEmployeeList();

		List<EmployeeDto> employeeDtoList = new ArrayList<EmployeeDto>();
		for(Employee e : employeeList) {
			EmployeeDto dto = new EmployeeDto().toDto(e);
			dto.setDept_code(e.getDepartment().getDeptCode());
			dto.setDept_name(e.getDepartment().getDeptName());
			dto.setJob_code(e.getJob().getJobCode());
			dto.setJob_name(e.getJob().getJobName());
			employeeDtoList.add(dto);
		}
		return employeeDtoList;
	}
	
	public EmployeeDto selectEmployeeOne(String writer_id) {
		Employee employee = employeeRepository.findByempId(writer_id);
		EmployeeDto dto = null;
		if(employee!=null) {
			dto = new EmployeeDto().toDto(employee);
		}
		return dto;
	}
	
	public List<EmployeeDto> selectEmployeeByNotTeacher(String t_year) {
		List<EmployeeVo> EmployeeByNotTeach = employeeVoMapper.selectEmployeeByNotTeacher(t_year);
		
		List<EmployeeDto> resultList = new ArrayList<EmployeeDto>();
		
		for(EmployeeVo e : EmployeeByNotTeach) {
			EmployeeDto dto = new EmployeeDto().toDto(e);
			
			Department dept = departmentRepository.findByDeptCode(dto.getDept_code());
			Job job = jobRepository.findByJobCode(dto.getJob_code());
			
			dto.setDept_name(dept.getDeptName());
			dto.setJob_name(job.getJobName());
			
			resultList.add(dto);
		}
		
		return resultList;
	}
	
	public int checkPw(String pwd) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		
		int result = -1;
		
		Employee employee = employeeRepository.findByempId(user.getUsername());
		
		System.out.println(passwordEncoder.encode(pwd));
		System.out.println(employee.getEmpPw());
		
		if(passwordEncoder.encode(pwd) == employee.getEmpPw()) {
			result = 1;
		}
		
		return result;
	}

}
