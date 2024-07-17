package com.example.vicenteytech.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.vicenteytech.dto.StockMovementDTO;
import com.example.vicenteytech.entities.Item;
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
	private final ModelMapper modelMapper;
	
	public StockMovement save(StockMovementDTO stockMovementDTO) {
		log.info("Saving StockMovement...");
		
		StockMovement stockMovement = modelMapper.map(stockMovementDTO, StockMovement.class);
		Item item = modelMapper.map(stockMovementDTO.getItem(), Item.class);
		stockMovement.setItem(item);
		return stockMovementRepository.save(stockMovement);
		
	}
	
	public StockMovement update(StockMovementDTO stockMovementDTO, Long idStock) {
		log.info("Updating StockMovement...");		
		StockMovement stockMovementSaved = getStockMovementById(idStock);
		stockMovementDTO.setId(stockMovementSaved.getId());
		
		return this.save(stockMovementDTO);
	}
	
	public StockMovement getStockMovementById(Long idStock) {
		log.info("StockMovement was not found ID: {}", idStock);
		
		StockMovement stockMovement = stockMovementRepository.findById(idStock).get();
		if(stockMovement == null) {
			log.error("StockMovement was not found ID: {}", idStock);
			throw new StockMovementException("StockMovement was not found.");
		}
		
		return stockMovement;
	}
	
	
	public void delete(Long idStock) {
		log.info("Deleting item with ID: {}", idStock);
		
		StockMovement stock = getStockMovementById(idStock);
		stockMovementRepository.delete(stock);
	}
	
	
}
