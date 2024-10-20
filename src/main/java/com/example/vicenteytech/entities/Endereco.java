package com.example.vicenteytech.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "enderecos")
public class Endereco {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "morada1", length = 100)
	private String morada1;

	@Column(name = "morada2", length = 100)
	private String morada2;
	
	@Column(name = "telemovel1", length = 20, unique = true, updatable = true)
	private String telemovel1;
	
	@Column(name = "telemovel2", length = 20, unique = true, updatable = true)
	private String telemovel2;
	
	@ManyToOne
	@JoinColumn(name = "municipio_id")
	private Municipio municipio;
	
	@ManyToOne
	@JoinColumn(name = "user_id", unique = true, updatable = true)
	private UserModel user;
	

}
