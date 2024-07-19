package com.example.vicenteytech.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.vicenteytech.dto.StockMovementDTO;
import com.example.vicenteytech.entities.Item;
import com.example.vicenteytech.entities.Order;
import com.example.vicenteytech.entities.StockMovement;
import com.example.vicenteytech.exceptions.StockMovementException;
import com.example.vicenteytech.repositories.StockMovementRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StockMovementService {
	

	private final StockMovementRepository stockMovementRepository;
	private final ItemService itemService;
	
	private final ModelMapper modelMapper;
	
	public StockMovement save(StockMovementDTO stockMovementDTO) {
		log.info("Saving StockMovement...");
		
		StockMovement stockMovement = modelMapper.map(stockMovementDTO, StockMovement.class);
		Item item = itemService.getItemById(stockMovementDTO.getItem().getId());
		stockMovement.setItem(item);
		
		if(stockMovementDTO != null) {
			stockMovement.setCreationDate(LocalDate.now());
		}
		return stockMovementRepository.save(stockMovement);
		
	}
	
	public StockMovement update(StockMovementDTO stockMovementDTO, Long idStock) {
		log.info("Updating StockMovement...");		
		
		StockMovement stockMovementSaved = getStockMovementById(idStock);
		stockMovementDTO.setId(stockMovementSaved.getId());
		stockMovementDTO.setCreationDate(stockMovementSaved.getCreationDate());
		
		return this.save(stockMovementDTO);
	}
	
	public List<StockMovement> getStockMovements(){
		log.info("Listing all StockMovement...");	
		return this.stockMovementRepository.findAll();
	}
	
	public StockMovement getStockMovementById(Long idStock) {
		log.info("Finding StockMovement with ID: {}", idStock);
		
		StockMovement stockMovement = stockMovementRepository.findById(idStock).get();
		if(stockMovement == null) {
			log.error("StockMovement was not found ID: {}", idStock);
			throw new StockMovementException("StockMovement was not found.");
		}
		
		return stockMovement;
	}
	
	public StockMovement getStockMovementByItem(Item item) {
		log.info("Finding StockMovement with ID: {}", item.getId());
		
		StockMovement stockMovement = stockMovementRepository.findByItem(item);
		if(stockMovement == null) {	
		log.error("To continue, please add This item to StockMovement. ID: {}", item.getId() );
		throw new StockMovementException("To continue, please add This item to StockMovement. ID:"+item.getId());
		
		}
		
		return stockMovement;
	}
	
	public void takeItem(Item item, Order order) {
		log.info("Taking item from StockMovement ID: {}", item.getId());
		
		StockMovement itemStock = this.getStockMovementByItem(item);
		 if (isValidItemAndQuantity(itemStock, order)) {
			
			log.error("We only have {} of the item(s) {}",itemStock.getQuantity(), itemStock.getItem().getName() );
			throw new StockMovementException(
					"We only have "+itemStock.getQuantity()+" of the item(s) "+itemStock.getItem().getName());
		}else {
			Integer quantity = itemStock.getQuantity() - order.getQuantity();
			itemStock.setQuantity(quantity);
			StockMovementDTO stockMovementDTO = modelMapper.map(itemStock, StockMovementDTO.class);
			this.save(stockMovementDTO);
		}
		
	}
	
	
	public void delete(Long idStock) {
		log.info("Deleting item with ID: {}", idStock);
		
		StockMovement stock = getStockMovementById(idStock);
		stockMovementRepository.delete(stock);
	}
	
	private boolean isValidItemAndQuantity(StockMovement itemStock, Order order) {
		return itemStock.getQuantity() < order.getQuantity();
	}
	
}
