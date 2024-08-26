package com.example.vicenteytech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.vicenteytech.service.EmailService;

@SpringBootApplication
public class VicenteYtechApplication implements CommandLineRunner{

	@Autowired
	private EmailService emailService;
	public static void main(String[] args) {
		SpringApplication.run(VicenteYtechApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		emailService.sendSimpleMessage("antonio@gmail.com", "Test", "Esta e a mensagem");
		emailService.sendEmailWithLink("antonio@gmail.com", "link", "google.com");
		
	}

}
