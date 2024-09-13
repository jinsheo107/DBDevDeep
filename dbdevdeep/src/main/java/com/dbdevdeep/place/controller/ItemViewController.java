package com.dbdevdeep.place.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dbdevdeep.place.domain.ItemDto;
import com.dbdevdeep.place.service.ItemService;
import com.dbdevdeep.place.service.PlaceService;

@Controller
public class ItemViewController {

	private final ItemService itemService;
	private final PlaceService placeService;
	
	
	@Autowired
	public ItemViewController(ItemService itemService, PlaceService placeService) {
		this.itemService = itemService;
		this.placeService = placeService;
	}
	
	
	// 목록조회
	@GetMapping("/item")
	public String selectItemList(Model model,ItemDto itemDto) {
		List<ItemDto> resultList = itemService.selectItemList(itemDto);
		model.addAttribute("resultList",resultList);
		
		// 다른 동적으로 계산된 값도 전달 가능
				for (ItemDto dto : resultList) {
					int availableQuantity = dto.getItem_quantity() - dto.getUnuseable_quantity();
					model.addAttribute("availableQuantity_" + dto.getItem_no(), availableQuantity);
				}
		
		return "place/item_list";
	}
}
