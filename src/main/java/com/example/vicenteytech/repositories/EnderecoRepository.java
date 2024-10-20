package com.example.vicenteytech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vicenteytech.entities.Endereco;
import com.example.vicenteytech.entities.UserModel;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
	Endereco findByUser(UserModel user);
}
