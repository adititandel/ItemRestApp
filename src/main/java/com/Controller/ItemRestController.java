package com.Controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.ItemDAO;
import com.model.Item;

@RestController
public class ItemRestController {
	
	@Autowired
	ItemDAO idao;
	
	@GetMapping("/homeinfo")
	public String gethomeinfo() {
		return "API working fine";
	}
	
	@PostMapping("/additem")
	public ResponseEntity addItem(@RequestBody Item item) {
		idao.save(item);
		
		return new ResponseEntity("item added",HttpStatus.OK);
	}
	
	@GetMapping("/getallitems")
	public List<Item> getAllItems(){
		return idao.findAll();
	}
	
	@PatchMapping("/updateitem")
	public ResponseEntity updateItem(@RequestBody Item item) {
		idao.saveAndFlush(item);//send id to update
		return new ResponseEntity("item updated"+item,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/deleteitem")
	public ResponseEntity deleteItem(@RequestBody Item item) {
		idao.delete(item);
		return new ResponseEntity("item deleted",HttpStatus.OK);
	}
	
	@GetMapping("/findbyid/{itemId}")
	public Item getItem(@PathVariable int itemId) {
		Item it=idao.findById(itemId).get();
		return it;
	}

}
