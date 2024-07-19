package com.example.vicenteytech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vicenteytech.entities.Item;
import com.example.vicenteytech.entities.StockMovement;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long>{
	StockMovement findByItem(Item item);
}
