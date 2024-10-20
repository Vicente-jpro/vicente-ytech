package com.example.vicenteytech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vicenteytech.entities.Provincia;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long>{

}
