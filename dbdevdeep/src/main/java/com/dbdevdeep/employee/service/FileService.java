package com.dbdevdeep.employee.service;

import java.io.File;
import java.net.URLDecoder;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbdevdeep.employee.domain.Employee;
import com.dbdevdeep.employee.repository.EmployeeRepository;


@Service
public class FileService {

private String fileDir = "C:\\employee\\upload\\";
	
	private final EmployeeRepository employeeRepository;
	
	@Autowired
	public FileService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	public int delete(String emp_id){
		int result = -1;
		
		try {
			Employee e = employeeRepository.findByempId(emp_id);
			String newFileName = e.getNewPicName();	// UUID
			String oriFileName = e.getOriPicName();	// 사용자가 아는 파일명
			String resultDir = fileDir + URLDecoder.decode(newFileName,"UTF-8");
			if(resultDir != null && resultDir.isEmpty() == false) {
				File file = new File(resultDir);
				if(file.exists()) {
					file.delete();
					result = 1;
				}	
			}	
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String upload(MultipartFile file) {
		
		String newFileName = null;
		
		try {
			// 1. 파일 원래 이름
			String oriFileName = file.getOriginalFilename();
			// 2. 파일 자르기
			String fileExt = oriFileName.substring(oriFileName.lastIndexOf("."),oriFileName.length());
			// 3. 파일 명칭 바꾸기
			UUID uuid = UUID.randomUUID();
			// 4. 8자리마다 포함되는 하이픈 제거
			String uniqueName = uuid.toString().replaceAll("-", "");
			// 5. 새로운 파일명
			newFileName = uniqueName+fileExt;
			// 6. 파일 저장 경로 설정
			// 7. 파일 껍데기 생성
			File saveFile = new File(fileDir+newFileName);
			// 8. 경로 존재 여부 확인
			if(!saveFile.exists()) {
				saveFile.mkdirs();
			}
			// 9. 껍데기에 파일 정보 복제
			file.transferTo(saveFile);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return newFileName;
	}
}
