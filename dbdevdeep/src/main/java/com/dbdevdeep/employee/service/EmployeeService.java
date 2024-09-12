package com.dbdevdeep.employee.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbdevdeep.employee.domain.Department;
import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.domain.EmployeeDto;
import com.dbdevdeep.employee.domain.Job;
import com.dbdevdeep.employee.domain.MySign;
import com.dbdevdeep.employee.domain.MySignDto;
import com.dbdevdeep.employee.mybatis.mapper.EmployeeVoMapper;
import com.dbdevdeep.employee.repository.DepartmentRepository;
import com.dbdevdeep.employee.repository.EmployeeRepository;
import com.dbdevdeep.employee.repository.JobRepository;
import com.dbdevdeep.employee.repository.MySignRepository;
import com.dbdevdeep.employee.vo.EmployeeVo;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder passwordEncoder;
	private final JobRepository jobRepository;
	private final DepartmentRepository departmentRepository;
	private final EmployeeVoMapper employeeVoMapper;
	private final MySignRepository mySignRepository;

	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder,
			JobRepository jobRepository, DepartmentRepository departmentRepository,
			EmployeeVoMapper employeeVoMapper, 
			MySignRepository mySignRepository) {
		this.employeeRepository = employeeRepository;
		this.passwordEncoder = passwordEncoder;
		this.jobRepository = jobRepository;
		this.departmentRepository = departmentRepository;
		this.employeeVoMapper = employeeVoMapper;
		this.mySignRepository = mySignRepository;
	}

	// 교육청관리번호 중복 확인
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

	// 직원 등록
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
	
	// 재직 상태가 Y인 직원 목록
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
	
	// 반을 담당하지 않은 교무부 직원 노출
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
	
	// 비밀번호 찾기
	public int checkPw(String pwd) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		
		int result = -1;
		
		Employee employee = employeeRepository.findByempId(user.getUsername());
		
		if(passwordEncoder.matches(pwd, employee.getEmpPw())) {
			result = 1;
		}
		
		return result;
	}
	
	// 서명 찾기
	public List<MySignDto> employeeSignGet(String emp_id) {
		List<MySignDto> resultList = new ArrayList<MySignDto>();
		
		try {
			List<MySign> signList = mySignRepository.mySignfindAllByEmpid(emp_id);
			
			for(MySign sign : signList) {
				MySignDto dto = new MySignDto().toDto(sign);
				resultList.add(dto);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return resultList;
	}
	
	// 서명 등록
	public int employeeSignAdd(MySignDto dto) {
		int result = -1;
		
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User)authentication.getPrincipal();
			
			Employee employee = employeeRepository.findByempId(user.getUsername());
			
			LocalDateTime ldt = LocalDateTime.now();
			
			if(dto.getRep_yn() != null) {
				List<MySign> signList = mySignRepository.findByEmpIdAndRepYn(user.getUsername());
				
				if(signList != null) {
					for(int i = 0; i < signList.size(); i++) {
						MySignDto msDto = new MySignDto().toDto(signList.get(i));
												
						MySign ms = MySign.builder()
								.signNo(msDto.getSign_no())
								.signTitle(msDto.getSign_title())
								.signType(msDto.getSign_type())
								.oriPicName(msDto.getOri_pic_name())
								.newPicName(msDto.getNew_pic_name())
								.regTime(msDto.getReg_time())
								.modTime(msDto.getMod_time())
								.repYn("N")
								.employee(employee)
								.build();
						
						mySignRepository.save(ms);
					}
				}
			}
			
			if(dto.getReg_time() != null) {
				dto.setMod_time(ldt);
			}
			
			MySign ms = MySign.builder()
					.signNo(dto.getSign_no())
					.signTitle(dto.getSign_title())
					.signType(dto.getSign_type())
					.oriPicName(dto.getOri_pic_name())
					.newPicName(dto.getNew_pic_name())
					.regTime(dto.getReg_time())
					.modTime(dto.getMod_time())
					.repYn(dto.getRep_yn() == null ? "N" : "Y")
					.employee(employee)
					.build();
			
			mySignRepository.save(ms);
			
			result = 1;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteSign(Long sign_no) {
		int result = 0;
		try {
			mySignRepository.deleteById(sign_no);	
			result = 1;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 상태메세지 수정
	@Transactional
	public int editChatStatus(String emp_id, String chat_status_msg){
		int result = -1;
		result = employeeRepository.updateByEmpIdToStatus(emp_id,chat_status_msg);
		return result;
	}
	
	// 내 정보 수정
	public Employee editMyInfo(EmployeeDto dto) {
		EmployeeDto temp = selectEmployeeOne(dto.getEmp_id());
		
		temp.setEmp_phone(dto.getEmp_phone());
		temp.setEmp_detail_addr(dto.getEmp_detail_addr());
		temp.setEmp_addr(dto.getEmp_addr());
		temp.setEmp_post_code(dto.getEmp_post_code());
		
		if(dto.getOri_pic_name() != null) {
			temp.setOri_pic_name(dto.getOri_pic_name());
			temp.setNew_pic_name(dto.getNew_pic_name());
		}
		
		Department dept = departmentRepository.findByDeptCode(temp.getDept_code());
		Job job = jobRepository.findByJobCode(temp.getJob_code());
		
		
		Employee emp = Employee.builder()
				.empId(temp.getEmp_id()).empPw(temp.getEmp_pw()).govId(temp.getGov_id())
				.empName(temp.getEmp_name()).empRrn(temp.getEmp_rrn()).empPhone(temp.getEmp_phone())
				.oriPicName(temp.getOri_pic_name()).newPicName(temp.getNew_pic_name()).empPostCode(temp.getEmp_post_code())
				.empAddr(temp.getEmp_addr()).empDetailAddr(temp.getEmp_detail_addr())
				.empInternalPhone(temp.getEmp_internal_phone()).vacationHour(temp.getVacation_hour())
				.hireDate(temp.getHire_date()).endDate(temp.getEnd_date()).entStatus(temp.getEnt_status())
				.loginYn(temp.getLogin_yn()).accountStatus(temp.getAccount_status())
				.chatStatusMsg(temp.getChat_status_msg())
				.job(job).department(dept).build();
		
		Employee result = employeeRepository.save(emp);
		
		return result;
	}
	
	// 내 비밀번호 수정
	public Employee editMyPw(String newPw) {
		Employee e = null;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		
		Employee employee = employeeRepository.findByempId(user.getUsername());
		
		EmployeeDto temp = new EmployeeDto().toDto(employee);
		
		Department dept = departmentRepository.findByDeptCode(temp.getDept_code());
		Job job = jobRepository.findByJobCode(temp.getJob_code());
		
		Employee emp = Employee.builder()
				.empId(temp.getEmp_id()).empPw(passwordEncoder.encode(newPw)).govId(temp.getGov_id())
				.empName(temp.getEmp_name()).empRrn(temp.getEmp_rrn()).empPhone(temp.getEmp_phone())
				.oriPicName(temp.getOri_pic_name()).newPicName(temp.getNew_pic_name()).empPostCode(temp.getEmp_post_code())
				.empAddr(temp.getEmp_addr()).empDetailAddr(temp.getEmp_detail_addr())
				.empInternalPhone(temp.getEmp_internal_phone()).vacationHour(temp.getVacation_hour())
				.hireDate(temp.getHire_date()).endDate(temp.getEnd_date()).entStatus(temp.getEnt_status())
				.loginYn(temp.getLogin_yn()).accountStatus(temp.getAccount_status())
				.chatStatusMsg(temp.getChat_status_msg())
				.job(job).department(dept).build();
		
		e = employeeRepository.save(emp);
		
		return e;
	}

}
