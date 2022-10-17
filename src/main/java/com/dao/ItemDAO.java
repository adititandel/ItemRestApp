package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Item;

@Repository
public interface ItemDAO extends JpaRepository<Item,Integer> {

	Item findByPrice(float f);

	Item findByItemName(String string);
	

}
