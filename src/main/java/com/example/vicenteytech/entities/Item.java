package com.example.vicenteytech.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "id")
	private Long id;
	
	@Column(name = "name", unique = true)
	private String name;
	
	@JsonIgnore
	@OneToMany( mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<StockMovement> stocks = new ArrayList<>();
	
	@ManyToMany(mappedBy = "items")
	private Set<Order> orders = new HashSet<>();
	
}
