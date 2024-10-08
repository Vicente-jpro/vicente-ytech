package com.example.vicenteytech.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.vicenteytech.dto.ItemDTO;
import com.example.vicenteytech.enums.StatusOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "id")
	private Long id;
	
	@Column(name = "creation_date", unique = true)
	private LocalDate creationDate;

	@Column(name = "quantity")
	private Integer quantity;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "order_items",
	joinColumns = @JoinColumn(name = "order_id"),
    inverseJoinColumns = @JoinColumn(name = "item_id"))
	private List<Item> items = new ArrayList<Item>();
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status_order")
	private StatusOrder status;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserModel user;

}
