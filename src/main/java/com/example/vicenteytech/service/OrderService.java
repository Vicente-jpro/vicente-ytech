package com.example.vicenteytech.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.vicenteytech.dto.ItemDTO;
import com.example.vicenteytech.dto.OrderDTO;
import com.example.vicenteytech.entities.Item;
import com.example.vicenteytech.entities.Order;
import com.example.vicenteytech.entities.UserModel;
import com.example.vicenteytech.exceptions.OrderException;
import com.example.vicenteytech.repositories.OrderRepository;
import com.example.vicenteytech.util.CurrentUser;

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
		
	
		order.setItems(items);
		if(orderDTO.getId() == null) {
			order.setCreationDate(LocalDate.now());
		}
		
        UserModel user = modelMapper.map(orderDTO.getUser(), UserModel.class);
        order.setUser(user);
        
		return orderRepository.save(order);
		
	}
	
	public Order update(OrderDTO orderDTO, Long idOrder) {
		log.info("Updating Order...");		
		
		Order orderSaved = getOrderById(idOrder);
		Integer userId = orderSaved.getUser().getId();
		
		if (orderDTO.getUser().getId() != userId) {
			log.info("This order belongs to another user.");
			throw new OrderException("This order belongs to another user.");
		}else {
				orderDTO.setId(orderSaved.getId());
				
				List<ItemDTO> itemsDTO = orderDTO.getItems()
						.stream()
						.map( it -> {
							
							ItemDTO item = new ItemDTO();
							item.setId(it.getId());
			
							return item;
				}).collect(Collectors.toList());
				
				orderDTO.setItems(itemsDTO);
				orderDTO.setCreationDate(orderSaved.getCreationDate());
			}
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
