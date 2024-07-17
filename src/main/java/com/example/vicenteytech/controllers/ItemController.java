package com.example.vicenteytech.controllers;



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

import com.example.vicenteytech.entities.Item;
import com.example.vicenteytech.service.ItemService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
	
	private final ItemService itemService;
	
	@PostMapping
	@ApiOperation("Save an Item")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses({
		@ApiResponse(code = 201, message = "Item saved successfully."),
		@ApiResponse(code = 400, message = "Could not save the item." )
	})
	public Item salvar(@RequestBody Item item) {
		return itemService.salvar(item);
	}
	
	@PatchMapping("/{id_item}")
	@ApiOperation("Update item with id.")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses({
		@ApiResponse(code = 201, message = "Item saved successfully."),
		@ApiResponse(code = 400, message = "Could not update the item.")
	})
	public Item atualizar(@RequestBody Item item, @PathVariable("id_item") Long idItem) {
		
		return itemService.update(item, idItem);
	}
	
	
	@GetMapping("/{id_item}")
	@ApiOperation("Get an item with id.")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Item saved successfully."),
		@ApiResponse(code = 400, message = "Item do not exist.")
	})
	public Item getItemById(@PathVariable("id_item") Long idItem) {
		
		return itemService.getItemById(idItem);
	}
	
	@DeleteMapping("/{id_item}")
	@ApiOperation("Update item with id.")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Item Deleted successfully."),
		@ApiResponse(code = 400, message = "Error on deleting item.")
	})
	public void deleteById(@PathVariable("id_item") Long idItem) {
		
		itemService.delete(idItem);
	}
	
}
