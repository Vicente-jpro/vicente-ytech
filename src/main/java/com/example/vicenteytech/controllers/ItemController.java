package com.example.vicenteytech.controllers;



import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vicenteytech.dto.ItemDTO;
import com.example.vicenteytech.dto.ItemRequestDTO;
import com.example.vicenteytech.dto.ItemResponseDTO;
import com.example.vicenteytech.dto.StockMovementDTO;
import com.example.vicenteytech.entities.Item;
import com.example.vicenteytech.entities.StockMovement;
import com.example.vicenteytech.service.ItemService;
import com.example.vicenteytech.service.StockMovementService;
import com.example.vicenteytech.util.SelfLinkHateoas;

import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
	
	private final ItemService itemService;
	private final StockMovementService stockMovementService;
	private final ModelMapper modelMapper;
	
	@PostMapping
	/*
	@ApiOperation("Save an ItemDTO")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses({
		@ApiResponse(code = 201, message = "ItemDTO saved successfully."),
		@ApiResponse(code = 400, message = "Could not save the item." )
	})
	*/
	public ItemResponseDTO salvar(@RequestBody ItemRequestDTO itemRequestDTO) {
		
		Item item = modelMapper.map(itemRequestDTO.getItem(), Item.class);
		Item itemSaved = itemService.salvar(item);
		
		ItemDTO itemDTO = modelMapper.map(itemSaved, ItemDTO.class);
		itemRequestDTO.setItem(itemDTO);
		
		StockMovementDTO stockMovementDTO = modelMapper.map(itemRequestDTO, StockMovementDTO.class);
		StockMovement itemStok = stockMovementService.save(stockMovementDTO);
		
		ItemResponseDTO itemStockDTO = modelMapper.map(itemStok, ItemResponseDTO.class);
		itemDTO.setId(itemSaved.getId());
		
		return itemStockDTO;
	}
	
	@PatchMapping("/{id_item}")
	/*
	@ApiOperation("Update item with id.")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses({
		@ApiResponse(code = 201, message = "ItemDTO saved successfully."),
		@ApiResponse(code = 400, message = "Could not update the item.")
	})
	*/
	public ItemResponseDTO update(@RequestBody ItemRequestDTO itemRequestDTO, @PathVariable("id_item") Long idItem) {
		
		Item item = modelMapper.map(itemRequestDTO.getItem(), Item.class);
		Item itemSaved =  itemService.update(item, idItem);
		
		ItemDTO itemDTO = modelMapper.map(itemSaved, ItemDTO.class);
		itemRequestDTO.setItem(itemDTO);
		
		StockMovement sockMovement = stockMovementService.getStockMovementByItem(itemSaved);
		
		StockMovementDTO stockMovementDTO = modelMapper.map(itemRequestDTO, StockMovementDTO.class);
		StockMovement itemStok = stockMovementService.update(stockMovementDTO, sockMovement.getId());
		
		ItemResponseDTO itemStockDTO = modelMapper.map(itemStok, ItemResponseDTO.class);
		itemDTO.setId(itemSaved.getId());
		
		Link selfLink = SelfLinkHateoas.getLink(ItemResponseDTO.class, itemStockDTO.getId());
		itemStockDTO.add(selfLink);

		return itemStockDTO;
		
	}
	
	
	@GetMapping("/{id_item}")
	/*
	@ApiOperation("Get an item with id.")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponses({
		@ApiResponse(code = 200, message = "ItemDTO saved successfully."),
		@ApiResponse(code = 400, message = "ItemDTO do not exist.")
	})
	*/
	public ItemDTO getItemDTOById(@PathVariable("id_item") Long idItem) {
		
		Item itemSaved =  itemService.getItemById(idItem);
		ItemDTO itemDTO = modelMapper.map(itemSaved, ItemDTO.class);
		
		return itemDTO;
	}
	
	@GetMapping()
	/*
	@ApiOperation("Get all item with id.")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponses({
		@ApiResponse(code = 200, message = "ItemDTO saved successfully."),
		@ApiResponse(code = 400, message = "ItemDTO do not exist.")
	})
	*/
	public List<ItemDTO> getItems() {
		
		return itemService.getItems()
				.stream()
				.map( item ->{
					ItemDTO itemDTO = modelMapper.map(item, ItemDTO.class);
					
					Link selfLink = SelfLinkHateoas.getLink(ItemDTO.class, itemDTO.getId());
					itemDTO.add(selfLink);
					
					return itemDTO;
		}).collect(Collectors.toList());
	}
	
	@DeleteMapping("/{id_item}")
	/*
	@ApiOperation("Update item with id.")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiResponses({
		@ApiResponse(code = 200, message = "ItemDTO Deleted successfully."),
		@ApiResponse(code = 400, message = "Error on deleting item.")
	})
	*/
	public void deleteById(@PathVariable("id_item") Long idItemDTO) {
		
		itemService.delete(idItemDTO);
	}
	
}
