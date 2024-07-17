package com.example.vicenteytech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vicenteytech.entities.Item;

@Repository
public interface ItemRespository extends JpaRepository<Item, Long>{

}
