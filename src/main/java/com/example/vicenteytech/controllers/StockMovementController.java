package com.example.vicenteytech.controllers;

import java.util.List;
import java.util.stream.Collectors;

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

import com.example.vicenteytech.dto.StockMovementDTO;
import com.example.vicenteytech.entities.StockMovement;
import com.example.vicenteytech.service.StockMovementService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock_movements")
public class StockMovementController {

	private final StockMovementService stockMovementService;
	private final ModelMapper modelMapper;

	@PostMapping
	@ApiOperation("Save an Stock Movement")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses({ @ApiResponse(code = 201, message = "Stock Movement saved successfully."),
			@ApiResponse(code = 400, message = "Could not save the Stock Movement.") })
	public StockMovementDTO save(@RequestBody StockMovementDTO stockMovementDTO) {

		StockMovement stock = stockMovementService.save(stockMovementDTO);
		StockMovementDTO stockDTO = modelMapper.map(stock, StockMovementDTO.class);

		return stockDTO;
	}

	@PatchMapping("/{id_stock}")
	@ApiOperation("Update Stock Movement with id.")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses({ @ApiResponse(code = 201, message = "Stock Movement saved successfully."),
			@ApiResponse(code = 400, message = "Could not update the Stock Movement.") })
	public StockMovementDTO update(@RequestBody StockMovementDTO stockMovementDTO,
			@PathVariable("id_stock") Long idStockMovementDTO) {

		StockMovement stock = stockMovementService.update(stockMovementDTO, idStockMovementDTO);
		StockMovementDTO stockDTO = modelMapper.map(stock, StockMovementDTO.class);

		return stockDTO;
	}

	@GetMapping("/{id_stock}")
	@ApiOperation("Get an Stock Movement with id.")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses({ @ApiResponse(code = 200, message = "Stock Movement saved successfully."),
			@ApiResponse(code = 400, message = "Stock Movement do not exist.") })
	public StockMovementDTO getStockMovementDTOById(@PathVariable("id_stock") Long idStockMovementDTO) {

		StockMovement stock = stockMovementService.getStockMovementById(idStockMovementDTO);
		StockMovementDTO stockDTO = modelMapper.map(stock, StockMovementDTO.class);

		return stockDTO;
	}

	@GetMapping
	@ApiOperation("Get all Stock Movement.")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses({ @ApiResponse(code = 200, message = "Stock Movement got successfully."), })
	public List<StockMovementDTO> getStockMovements() {

		return stockMovementService.getStockMovements().stream().map(itemStok -> {

			StockMovementDTO stockMovementDTO = modelMapper.map(itemStok, StockMovementDTO.class);
			return stockMovementDTO;

		}).collect(Collectors.toList());

	}

	@DeleteMapping("/{id_stock}")
	@ApiOperation("Update Stock Movement with id.")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses({ @ApiResponse(code = 200, message = "Stock Movement Deleted successfully."),
			@ApiResponse(code = 400, message = "Error on deleting Stock Movement.") })
	public void deleteById(@PathVariable("id_stock") Long idStockMovementDTO) {

		stockMovementService.delete(idStockMovementDTO);
	}

}
