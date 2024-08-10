package com.example.vicenteytech.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.vicenteytech.dto.UserDTO;
import com.example.vicenteytech.entities.UserModel;
import com.example.vicenteytech.exceptions.SenhaInvalidaException;
import com.example.vicenteytech.exceptions.UsuarioException;
import com.example.vicenteytech.repositories.UsuarioRepository;
import com.example.vicenteytech.util.CurrentUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public UserModel salvar(UserModel usuario){
    	log.info("Salvando o usuario...");
    	
    	
    	try {
    		return usuarioRepository.save(usuario);
		} catch (Exception e) {
			log.error("Email ja esta registado...");
			throw new UsuarioException("Email ja esta registado.");
		}
    	
    }

    public UserDetails autenticar( UserModel usuario ){
    	log.info("Authenticating the user..."); 
        UserDetails user = loadUserByUsername(usuario.getEmail());
        boolean senhasIguais = encoder.matches( usuario.getPassword(), user.getPassword() );

        if(senhasIguais){
            return user;
        }
        log.error("User Authenticatication: Invalid credentials.");
        throw new SenhaInvalidaException();
    }
    
    public UserModel autenticarEmail( UserModel usuario ){
    	log.info("Authenticating the user by email to receive reset password instruction..."); 
    	
    	UserModel user = usuarioRepository.findByEmail(usuario.getEmail()).get();
      
        if(user != null){
            return user;
        }

    	log.error("User do not exist."); 
        throw new UsernameNotFoundException("User do not exist.");
    }

    public UserModel findByTokenResetPassword(String token){
    	log.info("Finding user by token..."); 
    	UserModel user = usuarioRepository.findByTokenResetPassword(token);

        if(user != null){
            return user;
        }

    	log.error("Token has expired."); 
        throw new UsernameNotFoundException("Token has expired.");
    }

    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	UserModel usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

        String[] roles = usuario.isAdmin() ?
                new String[]{"ADMIN", "USER"} : new String[]{"USER"};

		UserDetails user = User
				.builder()
				.username(usuario.getEmail())
				.password(usuario.getPassword())
				.roles(roles)
				.build();

		return new CurrentUser(usuario);

    }
    
    public Object getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal(); 
          
            
            return userDetails.getAuthorities();
        } else {
            return null;
        }
    }

}
