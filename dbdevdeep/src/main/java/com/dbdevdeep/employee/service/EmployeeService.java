package com.dbdevdeep.employee.service;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.domain.EmployeeDto;
import com.dbdevdeep.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
		this.employeeRepository = employeeRepository;
		this.passwordEncoder = passwordEncoder;
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

		try {
			dto.setEmp_pw(passwordEncoder.encode(dto.getEmp_pw()));

			String currentYear = String.valueOf(dto.getHire_date());

			int count = Integer.parseInt(employeeRepository.findByempIdWhen(currentYear));

			System.out.println(count);
			
			int empYearCount = count + 1;

			String emp_id = empYearCount + "";

			dto.setEmp_id(emp_id);
			
			dto.setAccount_status("Y");
			dto.setLogin_yn("N");
			dto.setEnt_status("Y");
			dto.setVacation_time(120);

			Employee employee = dto.toEntity();
			employeeRepository.save(employee);
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
			employeeDtoList.add(dto);
		}
		return employeeDtoList;
	}

}
