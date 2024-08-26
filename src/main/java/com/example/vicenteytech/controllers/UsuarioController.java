package com.example.vicenteytech.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.vicenteytech.dto.CredenciaisDTO;
import com.example.vicenteytech.dto.TokenDTO;
import com.example.vicenteytech.dto.UserDTO;
import com.example.vicenteytech.dto.UserEmailDTO;
import com.example.vicenteytech.dto.UserPasswordRestDTO;
import com.example.vicenteytech.dto.UserResponseDTO;
import com.example.vicenteytech.entities.UserModel;
import com.example.vicenteytech.exceptions.SenhaInvalidaException;
import com.example.vicenteytech.exceptions.UsuarioException;
import com.example.vicenteytech.security.jwt.JwtService;
import com.example.vicenteytech.service.UsuarioServiceImpl;
import com.example.vicenteytech.util.CurrentUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    
    @Value("${security.password.reset}")
    private String urlPasswordReset;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO salvar( @RequestBody @Valid UserDTO userDTO ){
        String senhaCriptografada = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(senhaCriptografada);
        UserModel user = modelMapper.map(userDTO, UserModel.class);
        UserModel userSaved = usuarioService.salvar(user);
        
        userDTO.setId(userSaved.getId());
        UserResponseDTO useResponseDto = modelMapper.map(userDTO, UserResponseDTO.class);
        return useResponseDto;
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{
            
        	UserModel usuario = new UserModel();
                    usuario.setEmail(credenciais.getEmail());
                    usuario.setPassword(credenciais.getPassword());
            
            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getEmail(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
    
    @PostMapping("/password/new")
    public void passowrdNew(@RequestBody UserEmailDTO userEmail){
        try{
            
        	UserModel usuario = new UserModel();
                    usuario.setEmail(userEmail.getEmail());
                
            UserModel usuarioAutenticado = usuarioService.autenticarEmail(usuario);
            String token = jwtService.gerarToken(usuarioAutenticado);
            TokenDTO tokenReceived = new TokenDTO(usuario.getEmail(), token);
            
            usuarioAutenticado.setTokenResetPassword(token);
            this.usuarioService.salvar(usuarioAutenticado);
            
            //Send email to the user with this address.
            System.out.println(urlPasswordReset+tokenReceived.getToken());
            
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

    }
    
    @PostMapping("/password/reset")
    public void passowrdReset(@RequestBody UserPasswordRestDTO userPasswordRestDTO, @RequestParam("token") String token){
    
    	UserModel user = this.usuarioService.findByTokenResetPassword(token);
    	boolean tokenEquals = user.isTokenEquals(user.getTokenResetPassword(), token);
    	
    	if(tokenEquals) {
    		boolean passwordEqual = 
    				user.isPasswordEquals(
    						userPasswordRestDTO.getNewPassword(), 
    						userPasswordRestDTO.getConfirmePassword());
    		
    		if(passwordEqual) {
    		   String senhaCriptografada = passwordEncoder.encode(userPasswordRestDTO.getNewPassword());   		  
    		   user.setPassword(senhaCriptografada);
    		   this.usuarioService.salvar(user);
    		}else {
    			log.error("Password is diferent");
    			throw new UsuarioException("Password is diferent.");
    		}
    	}

    }
    
    @GetMapping("/current_user")
	public CurrentUser getAuthenticatedUser(Authentication authentication) {
		CurrentUser user = modelMapper.map(authentication.getPrincipal(), CurrentUser. class);
		if (user != null)
			return user;
		throw new UsernameNotFoundException("You need to loggin before authenticate.");
	}

}
