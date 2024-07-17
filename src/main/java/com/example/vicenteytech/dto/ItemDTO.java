package com.example.vicenteytech.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("name")
	private String name;
}
