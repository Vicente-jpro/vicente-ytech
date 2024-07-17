package com.example.vicenteytech.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "name", unique = true)
    @NotEmpty(message = "Username can not be empty.")
    private String name;
    
    @Column(name = "email", unique = true)
    @NotEmpty(message = "email can not be empty.")
    private String email;
    
    @Column(name = "passwrd")
    @NotEmpty(message = "Password can not be empty.")
    private String password;
    
    @Column(name = "admin")
    private boolean admin;

}
