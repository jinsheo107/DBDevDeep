package com.dbdevdeep.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dbdevdeep.employee.domain.Department;
import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.domain.EmployeeDto;
import com.dbdevdeep.employee.domain.Job;
import com.dbdevdeep.employee.repository.DepartmentRepository;
import com.dbdevdeep.employee.repository.EmployeeRepository;
import com.dbdevdeep.employee.repository.JobRepository;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder passwordEncoder;
	private final JobRepository jobRepository;
	private final DepartmentRepository departmentRepository;

	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder,
			JobRepository jobRepository, DepartmentRepository departmentRepository) {
		this.employeeRepository = employeeRepository;
		this.passwordEncoder = passwordEncoder;
		this.jobRepository = jobRepository;
		this.departmentRepository = departmentRepository;
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
			dto.setVacation_time(120);

			Employee e = Employee.builder()
					.empId(emp_id).empPw(dto.getEmp_pw()).govId(dto.getGov_id())
					.empName(dto.getEmp_name()).empNo(dto.getEmp_no()).empPhone(dto.getEmp_phone())
					.oriPic(dto.getOri_pic()).newPic(dto.getNew_pic()).empPost(dto.getEmp_post())
					.empAddr(dto.getEmp_addr()).empDetailAddr(dto.getEmp_detail_addr())
					.empInternalPhone(dto.getEmp_internal_phone()).vacationTime(dto.getVacation_time())
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
	
	public List<EmployeeDto> selectEmployeeList(EmployeeDto employeeDto) {
		List<Employee> employeeList = employeeRepository.findAll();

		List<EmployeeDto> employeeDtoList = new ArrayList<EmployeeDto>();
		for(Employee e : employeeList) {
			EmployeeDto dto = new EmployeeDto().toDto(e);
			dto.setDept_code(e.getDepartment().getDeptCode());
			dto.setDept_title(e.getDepartment().getDeptTitle());
			dto.setJob_code(e.getJob().getJobCode());
			dto.setJob_title(e.getJob().getJobTitle());
			employeeDtoList.add(dto);
		}
		return employeeDtoList;
	}

}
