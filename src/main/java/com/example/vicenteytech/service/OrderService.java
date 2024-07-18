package com.example.vicenteytech.service;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.vicenteytech.dto.OrderDTO;
import com.example.vicenteytech.entities.Item;
import com.example.vicenteytech.entities.Order;
import com.example.vicenteytech.entities.UserModel;
import com.example.vicenteytech.exceptions.OrderException;
import com.example.vicenteytech.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final ItemService itemService;
	
	private final ModelMapper modelMapper;
	
	public Order save(OrderDTO orderDTO) {
		log.info("Saving Order...");
		
		Order order = modelMapper.map(orderDTO, Order.class);
		List<Item> items = orderDTO.getItems()
				.stream()
				.map( it -> {
					
					Item item = itemService.getItemById(it.getId());
					
					return item;
				}).collect(Collectors.toList());
		
		Set<Item> itemsToSet = new LinkedHashSet<Item>(items);
		order.setItems(itemsToSet);
        order.setCreationDate(LocalDate.now());
        
        UserModel user = new UserModel();
        user.setId(1);
        order.setUser(user);
        
		return orderRepository.save(order);
		
	}
	
	public Order update(OrderDTO orderDTO, Long idOrder) {
		log.info("Updating Order...");		
		Order orderSaved = getOrderById(idOrder);
		orderDTO.setId(orderSaved.getId());
		
		return this.save(orderDTO);
	}
	
	public Order getOrderById(Long idOrder) {
		log.info("Order was not found ID: {}", idOrder);
		
		Order order = orderRepository.findById(idOrder).get();
		if(order == null) {
			log.error("Order was not found ID: {}", idOrder);
			throw new OrderException("Order was not found.");
		}
		
		return order;
	}
	
	
	public void delete(Long idOrder) {
		log.info("Deleting item with ID: {}", idOrder);
		
		Order stock = getOrderById(idOrder);
		orderRepository.delete(stock);
	}
	
	
}
