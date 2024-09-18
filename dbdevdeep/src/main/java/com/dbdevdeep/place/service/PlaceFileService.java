package com.dbdevdeep.place.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbdevdeep.place.domain.Place;
import com.dbdevdeep.place.repository.PlaceRepository;

@Service
public class PlaceFileService {
	
	// 파일 저장 경로 설정
	private String fileDir = "C:\\dbdevdeep\\";
	
	private final PlaceRepository placeRepository;
	
	@Autowired
	public PlaceFileService(PlaceRepository placeRepository) {
		this.placeRepository = placeRepository;
	}
	
	
	// 이미지 삭제
	public int delete(Long place_no) {
		int result = -1;
		
		try {
			Place p = placeRepository.findByplaceNo(place_no);
			
			String newPicName = p.getNewPicName();
			String resultDir = fileDir + "place\\" + URLDecoder.decode(newPicName,"UTF-8");
			
			
			if(newPicName == null || newPicName.isEmpty()) {
				// 파일명이 비어있거나, null일때
				return 1;
			}
			
			
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
	
	
	// 이미지 업로드
	public String upload(MultipartFile file) {
		
		String newPicName = null;
		
		try {
			// 1. 파일 원래 이름
			String oriPicName = file.getOriginalFilename();
			// 2. 파일 자르기 (자료형 떼서 UUID로 바꾸기 위해)
			String fileExt = oriPicName.substring(oriPicName.lastIndexOf("."),oriPicName.length());
			// 3. 파일 명칭 바꾸기
			UUID uuid = UUID.randomUUID();
			// 4. 8자리마다 포함되는 하이픈 제거
			String uniqueName = uuid.toString().replaceAll("-", "");
			// 5. 새로운 파일명
			newPicName = uniqueName + fileExt;
			// 7. 파일 껍데기 생성
			File saveFile = new File(fileDir  +"place\\" + newPicName);
			// 8. 경로 존재 여부 확인
			if(!saveFile.exists()) {
				saveFile.mkdirs();
			}
			// 9. 껍데기에 파일 정보 복제
			file.transferTo(saveFile);
		} catch (FileNotFoundException fnfe) {
	        // 파일이 존재하지 않을 때 처리 로직
	        System.err.println("파일을 찾을 수 없습니다: " + fnfe.getMessage());
	    } catch (IOException ioe) {
	        // IO 처리 문제 발생 시 처리 로직
	        System.err.println("파일 처리 중 문제가 발생했습니다: " + ioe.getMessage());
	    }
		return newPicName; 
	}
	
	
}
