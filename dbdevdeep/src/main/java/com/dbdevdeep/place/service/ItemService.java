package com.dbdevdeep.place.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.dbdevdeep.place.domain.Item;
import com.dbdevdeep.place.domain.ItemDto;
import com.dbdevdeep.place.repository.ItemRepository;
import com.dbdevdeep.place.repository.PlaceRepository;

@Service
public class ItemService {

	private final ItemRepository itemRepository;
	private final PlaceRepository placeRepository;
	
	@Autowired
	public ItemService(ItemRepository itemRepository, PlaceRepository placeRepository) {
		this.itemRepository = itemRepository;
		this.placeRepository = placeRepository;
	}
	
	
	//목록 조회
	public List<ItemDto> selectItemList(ItemDto itemDto){
		List<Item> itemList = itemRepository.findAll();
		List<ItemDto> itemDtoList = new ArrayList<ItemDto>();
		// 사용자 정보
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User user = (User) authentication.getPrincipal();
	    
	    for(Item i : itemList) {
	    	// 사용 가능 갯수 계산: 총 갯수 - 사용 불가 갯수
            int availableQuantity = i.getItemQuantity() - i.getUnuseableQuantity();
            
	    	ItemDto dto = ItemDto.builder()
	    			.item_no(i.getItemNo())
	    			.place_no(i.getPlace().getPlaceNo())
	    			.place_name(i.getPlace().getPlaceName())
	    			.item_name(i.getItemName())
	    			.place_location(i.getPlace().getPlaceLocation())
	    			.item_status(i.getItemStatus())
	    			.item_quantity(i.getItemQuantity())
	    			
	    			.unuseable_quantity(i.getUnuseableQuantity())
	    			.build();
	    	
	    	
	    	
	    	itemDtoList.add(dto);
	    			
	    	
	    }
	    return itemDtoList;
	}
	
	
	
	
	
	
}
