package com.dbdevdeep.place.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbdevdeep.place.domain.Item;


public interface ItemRepository extends JpaRepository<Item, Long>{

	Item findByitemNo(Long item_no);
}
