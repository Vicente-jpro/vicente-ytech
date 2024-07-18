package com.example.vicenteytech.dto;

import java.util.List;

import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {
    
	@JsonProperty("id")
	private Integer id;
	
	@JsonProperty("name")
    private String name;

	@JsonProperty("email")
    private String email;
	
	@JsonProperty("password")
    private String password;
	
	@JsonProperty("admin")
    private boolean admin;
	
	@JsonProperty("orders")
	private List<OrderDto> orders;
}
