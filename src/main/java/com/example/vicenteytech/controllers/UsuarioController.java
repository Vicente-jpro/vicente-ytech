package com.example.vicenteytech.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.vicenteytech.dto.CredenciaisDTO;
import com.example.vicenteytech.dto.TokenDTO;
import com.example.vicenteytech.dto.UserDTO;
import com.example.vicenteytech.entities.UserModel;
import com.example.vicenteytech.exceptions.SenhaInvalidaException;
import com.example.vicenteytech.security.jwt.JwtService;
import com.example.vicenteytech.service.UsuarioServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO salvar( @RequestBody @Valid UserDTO userDTO ){
        String senhaCriptografada = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(senhaCriptografada);
        UserModel user = modelMapper.map(userDTO, UserModel.class);
        UserModel userSaved = usuarioService.salvar(user);
        userDTO.setId(userSaved.getId());
        
        return userDTO;
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

}
