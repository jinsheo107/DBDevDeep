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
import com.dbdevdeep.place.domain.Place;
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
	
	
	// 정규식 중복여부
	

    public boolean checkSerialExists(Long placeNo, String serialNo) {
        return itemRepository.existsByPlacePlaceNoAndItemSerialNo(placeNo, serialNo);
    }
	
	// 등록
	
	public int createItem(ItemDto dto) {
			int result = -1;
		try {
			
			Place p = placeRepository.findByplaceNo(dto.getPlace_no());
			
			Item i = Item.builder()
					
					.place(p)
					.itemSerialNo(dto.getItem_serial_no())
					.itemName(dto.getItem_name())
					.itemContent(dto.getItem_content())
					.itemQuantity(dto.getItem_quantity())
					.itemStatus(dto.getItem_status())
					.unuseableReason(dto.getUnuseable_reason())
					.unuseableQuantity(dto.getUnuseable_quantity())
					.unuseableStartDate(dto.getUnuseable_start_date())
					.unuseableEndDate(dto.getUnuseable_end_date())
					.oriPicName(dto.getOri_pic_name() != null ? dto.getOri_pic_name() : "Default oriPicname")
	                .newPicName(dto.getNew_pic_name() != null ? dto.getNew_pic_name() : "Default newPicname")
					.regDate(dto.getReg_date())
					.modDate(dto.getMod_date())
					.build();
			
			itemRepository.save(i);
			result = 1;
			
		}catch(Exception e) {
			e.printStackTrace();;
		}
		return result;
	}
	
	
	// 장소별 항목 조회 메서드 추가
		public List<ItemDto> selectItemsByPlace(Long placeNo) {
			List<Item> itemList = itemRepository.findByPlacePlaceNo(placeNo);  // 장소 번호로 항목 조회
			List<ItemDto> itemDtoList = new ArrayList<>();

			for(Item i : itemList) {
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
