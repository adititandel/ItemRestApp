package com.itemrestapp.itemrestapp;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.dao.ItemDAO;
import com.model.Item;

@SpringBootTest
class ItemrestappApplicationTest {
	
	@Autowired
	ItemDAO idao;
	

	@Test
	void testadditem() {
		Item i1=new Item();
		i1.setItemId(1);
		i1.setItemName("Milk");
		i1.setPrice(10);
		i1.setQuantity(10);
		idao.save(i1);
		
		Item actual=idao.findById(1).get();
		
		Assert.assertEquals(i1.getItemName(),actual.getItemName());
		//Assertions.assertEquals(i1.getPrice(),actual.getPrice());
		//Assertions.assertEquals(i1.getQuantity(),actual.getQuantity());
	}
	
	@Test
	void testfindbyprice() {
		Item i1=new Item();
		i1.setItemId(1);
		i1.setItemName("Milk");
		i1.setPrice(10);
		i1.setQuantity(10);
		
		idao.save(i1);
		Item expected=idao.findByPrice(10);
		
		Assertions.assertEquals("Milk",expected.getItemName());
	}
	
	@Test
	void testupdate() {
		Item i1=new Item();
		i1.setItemId(1);
		i1.setItemName("Milk");
		i1.setPrice(10);
		i1.setQuantity(10);
		idao.save(i1);
		
		Item i2=new Item();
		i2.setItemId(1);
		i2.setItemName("Cow Milk");
		i2.setPrice(10);
		i2.setQuantity(10);
		idao.saveAndFlush(i2);
		
		Item expected=idao.findByItemName("Cow Milk");
		
		Assertions.assertEquals("Cow Milk", expected.getItemName());
		
	}
	
	@Test 
	void testdelete() {
		Item i1=new Item();
		i1.setItemId(1);
		i1.setItemName("Milk");
		i1.setPrice(10);
		i1.setQuantity(10);
		idao.save(i1);
		
		Item i2=new Item();
		i2.setItemId(2);
		i2.setItemName("Cow Milk");
		i2.setPrice(20);
		i2.setQuantity(20);
		idao.save(i2);
		
		idao.deleteById(2);
		
		long len=idao.count();
		
		Assertions.assertEquals(1,len);
		
	}
	
	
	@Test
	void testcontrollerfindbyid() throws URISyntaxException {
		RestTemplate temp=new RestTemplate();
		final String url="http://localhost:8080/findbyid/1";
		URI uri=new URI(url);
		ResponseEntity <String> res=temp.getForEntity(uri, String.class);
		Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
	}
	
	@Test
	void testcontrollergetall() throws URISyntaxException {
		RestTemplate temp=new RestTemplate();
		final String url="http://localhost:8080/getallitems";
		URI uri=new URI(url);
		ResponseEntity <String> res=temp.getForEntity(uri, String.class);
		Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
	}
	
	@Test
	void testcontrolleradditem() throws URISyntaxException {
		RestTemplate temp=new RestTemplate();
		final String url="http://localhost:8080/additem";
		Item i1=new Item();
		i1.setItemId(3);
		i1.setItemName("Cheese");
		i1.setPrice(30);
		i1.setQuantity(30);
		
		URI uri=new URI(url);
		
		HttpHeaders headers=new HttpHeaders();
		HttpEntity<Item> req=new HttpEntity<>(i1,headers);
		ResponseEntity <String> res=temp.postForEntity(uri,req, String.class);
		Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
	}
	
	

}
