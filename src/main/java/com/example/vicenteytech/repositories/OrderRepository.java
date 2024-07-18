package com.example.vicenteytech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vicenteytech.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
