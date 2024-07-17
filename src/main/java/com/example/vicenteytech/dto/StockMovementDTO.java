package com.example.vicenteytech.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockMovementDTO {

	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("creation_date")
	private LocalDate creationDate;
	
	@JsonProperty("quantity")
	private Integer quantity;
	
	@JsonProperty("item")
	private ItemDTO item;

}
