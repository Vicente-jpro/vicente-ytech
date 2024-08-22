package com.example.vicenteytech.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.example.vicenteytech.entities.Item;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponseDTO extends RepresentationModel<ItemResponseDTO>{

	@JsonProperty("id")
	private Long id;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty("creation_date")
	private LocalDate creationDate;
	
	@JsonProperty("quantity")
	private Integer quantity;
	
	@JsonProperty("item")
	private ItemDTO item;

}
