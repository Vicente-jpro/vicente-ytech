package com.example.vicenteytech.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vicenteytech.entities.Order;
import com.example.vicenteytech.entities.UserModel;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	List<Order> findAllByUser(UserModel user);
}
