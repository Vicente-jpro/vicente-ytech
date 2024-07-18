package com.example.vicenteytech.dto;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("quantity")
	private Integer quantity;
	
	@JsonProperty("items")
	private Set<ItemDTO> items = new HashSet<ItemDTO>();
	
}
