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

import com.example.vicenteytech.dto.ItemDTO;
import com.example.vicenteytech.dto.StockMovementDTO;
import com.example.vicenteytech.entities.Item;
import com.example.vicenteytech.entities.StockMovement;
import com.example.vicenteytech.service.ItemService;
import com.example.vicenteytech.service.StockMovementService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
	
	private final ItemService itemService;
	private final StockMovementService stockMovementService;
	private final ModelMapper modelMapper;
	
	@PostMapping
	@ApiOperation("Save an ItemDTO")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses({
		@ApiResponse(code = 201, message = "ItemDTO saved successfully."),
		@ApiResponse(code = 400, message = "Could not save the item." )
	})
	public ItemDTO salvar(@RequestBody StockMovementDTO stockDTO) {
		
		Item item = modelMapper.map(stockDTO.getItem(), Item.class);
		Item itemSaved = itemService.salvar(item);
		
		ItemDTO itemDTO = modelMapper.map(itemSaved, ItemDTO.class);
		stockDTO.setItem(itemDTO);
		stockMovementService.save(stockDTO);
		
		itemDTO.setId(itemSaved.getId());
		
		return itemDTO;
	}
	
	@PatchMapping("/{id_item}")
	@ApiOperation("Update item with id.")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses({
		@ApiResponse(code = 201, message = "ItemDTO saved successfully."),
		@ApiResponse(code = 400, message = "Could not update the item.")
	})
	public ItemDTO atualizar(@RequestBody StockMovementDTO stockDTO, @PathVariable("id_item") Long idItem) {
		
		Item item = modelMapper.map(stockDTO.getItem(), Item.class);
		Item itemSaved =  itemService.update(item, idItem);
		
		ItemDTO itemDTO = modelMapper.map(itemSaved, ItemDTO.class);
		stockDTO.setItem(itemDTO);
		StockMovement sockMovement = stockMovementService.getStockMovementByItem(itemSaved);
		
		
		stockMovementService.update(stockDTO, sockMovement.getId());
		
		itemDTO.setId(itemSaved.getId());
		
		
		return itemDTO;
		
	}
	
	
	@GetMapping("/{id_item}")
	@ApiOperation("Get an item with id.")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses({
		@ApiResponse(code = 200, message = "ItemDTO saved successfully."),
		@ApiResponse(code = 400, message = "ItemDTO do not exist.")
	})
	public ItemDTO getItemDTOById(@PathVariable("id_item") Long idItem) {
		
		Item itemSaved =  itemService.getItemById(idItem);
		ItemDTO itemDTO = modelMapper.map(itemSaved, ItemDTO.class);
		
		return itemDTO;
	}
	
	@DeleteMapping("/{id_item}")
	@ApiOperation("Update item with id.")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses({
		@ApiResponse(code = 200, message = "ItemDTO Deleted successfully."),
		@ApiResponse(code = 400, message = "Error on deleting item.")
	})
	public void deleteById(@PathVariable("id_item") Long idItemDTO) {
		
		itemService.delete(idItemDTO);
	}
	
}
