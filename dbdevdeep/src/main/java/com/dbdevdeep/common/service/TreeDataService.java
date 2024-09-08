package com.dbdevdeep.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.repository.EmployeeRepository;

@Service
public class TreeDataService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public TreeDataService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Map<String, Object>> getTreeData(String year) {
        // employee 의 모든 데이터 가져오기
        List<Employee> employees = employeeRepository.findAllByYear(year);

        // 트리구조 노드 생성
        // 1. 최상위 노드 생성 (교장)
        Optional<Employee> principal = employees.stream()
            .filter(emp -> emp.getDepartment() != null 
                        && emp.getJob() != null 
                        && "D1".equals(emp.getDepartment().getDeptCode()) 
                        && "J1".equals(emp.getJob().getJobCode())) // 부서가 교장(D1)이고 직급이 교장(J1)인 경우
            .findFirst();

        List<Map<String, Object>> treeData = new ArrayList<>();

        // 교장 데이터가 존재할 경우에만 트리 구조 생성
        if (principal.isPresent()) {
            Employee principalEmployee = principal.get();

            Map<String, Object> principalNode = new HashMap<>();
            principalNode.put("text", principalEmployee.getEmpName() + "(" + principalEmployee.getJob().getJobName() + ")");
            principalNode.put("children", createVicePrincipalNode(employees)); // 교감 노드 생성
            treeData.add(principalNode);
        }

        return treeData;
    }

    private List<Map<String, Object>> createVicePrincipalNode(List<Employee> employees) {
        List<Map<String, Object>> children = new ArrayList<>();

        // 교감 데이터를 검색
        Employee vicePrincipalEmployee = employees.stream()
            .filter(emp -> "D2".equals(emp.getDepartment().getDeptCode()) && "J2".equals(emp.getJob().getJobCode())) // 부서가 교감실(D2)이고 직급이 교감(J2)인 경우
            .findFirst().orElse(null);

        if (vicePrincipalEmployee != null) {
            Map<String, Object> vicePrincipalNode = new HashMap<>();
            vicePrincipalNode.put("text", vicePrincipalEmployee.getEmpName() + "(" + vicePrincipalEmployee.getJob().getJobName() + ")");
            vicePrincipalNode.put("children", createDepartmentNodes(employees)); // 부서별 노드 생성
            children.add(vicePrincipalNode);
        } 

        return children;
    }

    private List<Map<String, Object>> createDepartmentNodes(List<Employee> employees) {
        List<Map<String, Object>> departmentNodes = new ArrayList<>();

        // 행정부 노드 생성
        Map<String, Object> executiveNode = new HashMap<>();
        executiveNode.put("text", "행정부");
        executiveNode.put("children", createAdminNodes(employees));
        departmentNodes.add(executiveNode);

        // 교무부 노드 생성
        Map<String, Object> studyNode = new HashMap<>();
        studyNode.put("text", "교무부");
        studyNode.put("children", createAcademicNodes(employees));
        departmentNodes.add(studyNode);

        return departmentNodes;
    }

    private List<Map<String, Object>> createAdminNodes(List<Employee> employees) {
        List<Map<String, Object>> adminNodes = new ArrayList<>();

        // 행정부 부장 노드 생성
        employees.stream()
            .filter(emp -> "D3".equals(emp.getDepartment().getDeptCode()) && "J3".equals(emp.getJob().getJobCode()))
            .forEach(emp -> {
                Map<String, Object> headNode = new HashMap<>();
                headNode.put("text", emp.getEmpName() + "(" + emp.getDepartment().getDeptName()+"-"+emp.getJob().getJobName()+ ")");
                headNode.put("children", createAdminEmployeeNodes(employees)); // 행정부 직원 노드 생성
                adminNodes.add(headNode);
            });

        return adminNodes;
    }

    private List<Map<String, Object>> createAdminEmployeeNodes(List<Employee> employees) {
        return employees.stream()
            .filter(emp -> "D3".equals(emp.getDepartment().getDeptCode()) && "J4".equals(emp.getJob().getJobCode())) // 행정부 부서의 직원
            .map(emp -> {
                Map<String, Object> empNode = new HashMap<>();
                empNode.put("text", emp.getEmpName() + "(" + emp.getEmpId() + ")");
                return empNode;
            }).collect(Collectors.toList());
    }

    private List<Map<String, Object>> createAcademicNodes(List<Employee> employees) {
        List<Map<String, Object>> academicNodes = new ArrayList<>();

        // 교무부장 노드 생성
        employees.stream()
            .filter(emp -> "D4".equals(emp.getDepartment().getDeptCode()) && "J3".equals(emp.getJob().getJobCode())) 
            .forEach(emp -> {
                Map<String, Object> headNode = new HashMap<>();
                headNode.put("text", emp.getEmpName() + "(" + emp.getDepartment().getDeptName()+"-"+emp.getJob().getJobName()+ ")");
                headNode.put("children", createAcademicGradeNodes(employees)); // 학년별 교직원 노드 생성
                academicNodes.add(headNode);
            });

        return academicNodes;
    }

    private List<Map<String, Object>> createAcademicGradeNodes(List<Employee> employees) {
        List<Map<String, Object>> gradeNodes = new ArrayList<>();

        for (int grade = 1; grade <= 6; grade++) {
            final int gradeNo = grade;

            Map<String, Object> gradeNode = new HashMap<>();
            gradeNode.put("text", gradeNo + "학년 교직원");

            // 각 학년별 교직원 노드 생성
            gradeNode.put("children", employees.stream()
                    .filter(emp -> "D4".equals(emp.getDepartment().getDeptCode()) && "J4".equals(emp.getJob().getJobCode()) // 교무부 직원 중 해당 학년 교직원
                        && emp.getTeacherHistorys() != null
                        && emp.getTeacherHistorys().stream()
                        .anyMatch(history -> history.getGrade() == gradeNo))
                    .map(emp -> {
                        Map<String, Object> empNode = new HashMap<>();
                        empNode.put("text", emp.getEmpName() + "(" + emp.getEmpId() + ")");
                        return empNode;
                    }).collect(Collectors.toList()));

                gradeNodes.add(gradeNode);
            }

            return gradeNodes;
    }
}
