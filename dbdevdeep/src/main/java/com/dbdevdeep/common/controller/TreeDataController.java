package com.dbdevdeep.common.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbdevdeep.common.service.TreeDataService;

@RestController
public class TreeDataController {

	private final TreeDataService treeDataService;

    @Autowired
    public TreeDataController(TreeDataService treeDataService) {
        this.treeDataService = treeDataService;
    }

    @GetMapping("/api/tree-data")
    public List<Map<String, Object>> getTreeData() {
        // 현재 연도를 문자열로 변환
        String currentYear = String.valueOf(LocalDate.now().getYear());

        // 서비스 메서드에 현재 연도를 전달하여 트리 데이터를 가져옴
        List<Map<String, Object>> treeData = treeDataService.getTreeData(currentYear);

        // 트리 데이터를 반환
        return treeData;
    }
}