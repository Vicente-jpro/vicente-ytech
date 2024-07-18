package com.example.vicenteytech.controllers;



import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.vicenteytech.dto.OrderDTO;
import com.example.vicenteytech.entities.Order;
import com.example.vicenteytech.service.OrderService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
	
	private final OrderService orderService;
	private final ModelMapper modelMapper;
	
	@PostMapping
	@ApiOperation("Save an Order")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses({
		@ApiResponse(code = 201, message = "Order saved successfully."),
		@ApiResponse(code = 400, message = "Could not save the Order." )
	})
	public OrderDTO save(@RequestBody OrderDTO orderDTO) {
	
		Order stock = orderService.save(orderDTO);
		OrderDTO stockDTO = modelMapper.map(stock, OrderDTO.class);
		
		return stockDTO;
	}
	
	@PatchMapping("/{id_order}")
	@ApiOperation("Update Order with id.")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses({
		@ApiResponse(code = 201, message = "Order saved successfully."),
		@ApiResponse(code = 400, message = "Could not update the Order.")
	})
	public OrderDTO update(@RequestBody OrderDTO orderDTO, @PathVariable("id_order") Long idOrder) {
		

		Order stock = orderService.update(orderDTO, idOrder);
		OrderDTO stockDTO = modelMapper.map(stock, OrderDTO.class);
		
		return stockDTO;
	}
	
	
	@GetMapping("/{id_order}")
	@ApiOperation("Get an Order with id.")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Order saved successfully."),
		@ApiResponse(code = 400, message = "Order do not exist.")
	})
	public OrderDTO getOrderById(@PathVariable("id_order") Long idOrder) {
		

		Order stock = orderService.getOrderById(idOrder);
		OrderDTO stockDTO = modelMapper.map(stock, OrderDTO.class);
		
		return stockDTO;
	}
	
	@DeleteMapping("/{id_order}")
	@ApiOperation("Update Order with id.")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Order Deleted successfully."),
		@ApiResponse(code = 400, message = "Error on deleting Order.")
	})
	public void deleteById(@PathVariable("id_order") Long idOrder) {
		
		orderService.delete(idOrder);
	}
	
}
