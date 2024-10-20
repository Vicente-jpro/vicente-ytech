package com.example.vicenteytech.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.vicenteytech.dto.OrderDTO;
import com.example.vicenteytech.dto.UserResponseDTO;
import com.example.vicenteytech.entities.Order;
import com.example.vicenteytech.enums.StatusOrder;
import com.example.vicenteytech.service.OrderService;
import com.example.vicenteytech.service.UsuarioServiceImpl;
import com.example.vicenteytech.util.CurrentUser;
import com.example.vicenteytech.util.LoggedInUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;
	private final ModelMapper modelMapper;
	private final UsuarioServiceImpl usuarioServiceImpl;
	
	/*
	@PostMapping
	@ApiOperation("Save an Order")
	@ApiResponses({ @ApiResponse(code = 201, message = "Order was saved successfully."),
			@ApiResponse(code = 400, message = "Could not save the Order.") })
	*/
	@ResponseStatus(HttpStatus.CREATED)
	public OrderDTO save(@RequestBody OrderDTO orderDTO, @LoggedInUser CurrentUser currentUser) {

		UserResponseDTO userDTO = UserResponseDTO.builder().id(currentUser.getUser().getId()).build();
		
		orderDTO.setStatus(StatusOrder.UNCOMPLETED);
		orderDTO.setUser(userDTO);
		Order stock = orderService.save(orderDTO);
		OrderDTO stockDTO = modelMapper.map(stock, OrderDTO.class);

		return stockDTO;
	}

	@GetMapping("/current")
	public Object getCurrentUser(Authentication authentication) {

		return authentication.getPrincipal();
	}

	@PatchMapping("/{id_order}")
	/*
	@ApiOperation("Update Order with id.")
	
	@ApiResponses({ @ApiResponse(code = 201, message = "Order was saved successfully."),
			@ApiResponse(code = 400, message = "Could not update the Order.") })
			*/
	@ResponseStatus(HttpStatus.CREATED)
	public OrderDTO update(@RequestBody OrderDTO orderDTO, @PathVariable("id_order") Long idOrder,
			@LoggedInUser CurrentUser currentUser) {

		UserResponseDTO userDTO = UserResponseDTO
				.builder()
					.id(currentUser.getUser().getId())
				.build();

		orderDTO.setStatus(StatusOrder.UNCOMPLETED);
		orderDTO.setUser(userDTO);
		Order stock = orderService.update(orderDTO, idOrder);
		OrderDTO stockDTO = modelMapper.map(stock, OrderDTO.class);

		return stockDTO;
	}
	
	
	@PatchMapping("/{id_order}/complete")
	/*
	@ApiOperation("Update Order with id.")
	@ApiResponses({ @ApiResponse(code = 201, message = "Order was saved successfully."),
			@ApiResponse(code = 400, message = "Could not update the Order.") })
	*/
	@ResponseStatus(HttpStatus.CREATED)
	public OrderDTO completeOrder(@RequestBody OrderDTO orderDTO, @PathVariable("id_order") Long idOrder,
			@LoggedInUser CurrentUser currentUser) {

		UserResponseDTO userDTO = UserResponseDTO
				.builder()
					.id(currentUser.getUser().getId())
				.build();

		orderDTO.setStatus(StatusOrder.COMPLETED);
		orderDTO.setUser(userDTO);
		
		Order stock = orderService.update(orderDTO, idOrder);
		OrderDTO stockDTO = modelMapper.map(stock, OrderDTO.class);

		return stockDTO;
	}

	@GetMapping("/{id_order}")
	/*
	@ApiOperation("Get an Order with id.")
	
	@ApiResponses({ @ApiResponse(code = 200, message = "Order was saved successfully."),
			@ApiResponse(code = 400, message = "Order do not exist.") })
	*/
	@ResponseStatus(HttpStatus.OK)
	public OrderDTO getOrderById(@PathVariable("id_order") Long idOrder) {

		Order stock = orderService.getOrderById(idOrder);
		OrderDTO stockDTO = modelMapper.map(stock, OrderDTO.class);

		return stockDTO;
	}
	
	@GetMapping()
	/*
	@ApiOperation("Get all Order by current_user.")
	@ApiResponse(code = 200, message = "Order was saved successfully.")
	*/
	@ResponseStatus(HttpStatus.OK)
	public List<OrderDTO> getOrders() {

		return orderService.getOrders()
				.stream()
				.map( order -> {
					OrderDTO ordeDTO = modelMapper.map(order, OrderDTO.class);
					return ordeDTO;
				}).collect(Collectors.toList());
	}

	@DeleteMapping("/{id_order}")
	/*
	@ApiOperation("Update Order with id.")
	@ApiResponses({ @ApiResponse(code = 200, message = "Order was Deleted successfully."),
			@ApiResponse(code = 400, message = "Error on deleting Order.") })
	*/
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable("id_order") Long idOrder) {

		orderService.delete(idOrder);
	}
	
	
	@GetMapping("/user")
//	@ApiOperation("Find orders by users.")
//	@ApiResponses({
//		@ApiResponse(code = 200, message = "Find orders by user"),
//		@ApiResponse(code = 400, message = "this user never created an order not.")
//	})
	public List<OrderDTO> getOrdersByUser(Authentication authentication) {
		CurrentUser user = modelMapper.map(authentication.getPrincipal(), CurrentUser.class);
		
		List<Order> orders = orderService.getOrdersByUser(user.getUser());
		
		return orders
				.stream()
				.map( order -> {
					OrderDTO ordeDTO = modelMapper.map(order, OrderDTO.class);
					return ordeDTO;
				}).collect(Collectors.toList());
		
		
	}

}
