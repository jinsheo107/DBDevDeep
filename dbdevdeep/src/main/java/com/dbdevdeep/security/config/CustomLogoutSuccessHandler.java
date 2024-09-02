package com.dbdevdeep.security.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.domain.EmployeeDto;
import com.dbdevdeep.employee.repository.EmployeeRepository;
import com.dbdevdeep.security.vo.SecurityUser;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	private final EmployeeRepository employeeRepository;

	@Autowired
	public CustomLogoutSuccessHandler(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		if (authentication != null && authentication.getPrincipal() instanceof SecurityUser) {
			SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
			String username = securityUser.getUsername();
			Employee employee = employeeRepository.findByempId(username);

			if (employee != null) {
				EmployeeDto employeeDto = new EmployeeDto().toDto(employee);

				employeeDto.setLogin_yn("N");

				employee = employeeDto.toEntity();

				employeeRepository.save(employee);
			}

			response.sendRedirect("/login");
		}

	}

}
