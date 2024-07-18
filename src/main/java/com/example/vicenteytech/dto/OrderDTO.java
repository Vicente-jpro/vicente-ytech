package com.example.vicenteytech.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.vicenteytech.entities.UserModel;
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
	
	@JsonProperty("creation_date")
	private LocalDate creationDate;
	
	@JsonProperty("items")
	private List<ItemDTO> items = new ArrayList<ItemDTO>();
	
	@JsonProperty("user")
	private UserResponseDTO user;
	
	
}
