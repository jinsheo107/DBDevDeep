package com.dbdevdeep.student.service;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StudentFileService {

	// 6. 파일 저장 경로 설정(위에 전역 변수로)
	private String fileDir = "C:\\student\\upload\\";
	
	public String upload(MultipartFile file) {
		
		String newFileName = null;
		
		try {
			// 1. 파일 원래 이름
			String oriFileName = file.getOriginalFilename();
			// 2. 파일 자르기 (자료형 떼서 UUID로 바꾸기 위해)
			String fileExt = oriFileName.substring(oriFileName.lastIndexOf("."),oriFileName.length());
			// 3. 파일 명칭 바꾸기
			UUID uuid = UUID.randomUUID();
			// 4. 8자리마다 포함되는 하이픈 제거
			String uniqueName = uuid.toString().replaceAll("-", "");
			// 5. 새로운 파일명
			newFileName = uniqueName+fileExt;
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
