package com.example.vicenteytech.service;

import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.vicenteytech.entities.Item;
import com.example.vicenteytech.exceptions.ItemException;
import com.example.vicenteytech.repositories.ItemRespository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

	private final ItemRespository itemRepositoy;
	
	public Item salvar(Item item) {
		log.info("Saving the item...");
		
		try {
			return itemRepositoy.save(item);
		} catch (Exception e) {

			log.error("Item already registered.");
			throw new ItemException("Item already registered.");
		}
		
	}
	
	public Item update(Item item, Long idItem) {
		log.info("Updating the item...");		
		
		Item itemSalvo = getItemById(idItem);
		item.setId(itemSalvo.getId());
		
		return this.salvar(item);
	}
	
	public Item getItemById(Long idItem) {
		log.info("Getting item with ID: {}", idItem);
		
		try {
			return itemRepositoy.findById(idItem).get();
		} catch (NoSuchElementException e) {

			log.error("Item not found ID: {}", idItem);
			throw new ItemException("Item not found.");
		}
		
	}
	
	public void delete(Long idItem) {
		log.info("Deleting item with ID: {}", idItem);
		
		Item item = getItemById(idItem);
		itemRepositoy.delete(item);
	}
	
	
}
