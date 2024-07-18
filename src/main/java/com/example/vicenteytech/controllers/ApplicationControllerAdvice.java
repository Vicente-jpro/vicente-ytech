package com.example.vicenteytech.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.vicenteytech.exceptions.ItemException;
import com.example.vicenteytech.exceptions.OrderException;
import com.example.vicenteytech.exceptions.StockMovementException;
import com.example.vicenteytech.exceptions.UsuarioException;
import com.example.vicenteytech.util.ApiErrors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(ItemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleItemExceptionException(ItemException ex){
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }

    @ExceptionHandler(UsuarioException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleUsuarioException( UsuarioException ex ){
        return new ApiErrors(ex.getMessage());
    }
    
    @ExceptionHandler(StockMovementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleStockMovementException( StockMovementException ex ){
        return new ApiErrors(ex.getMessage());
    }
    
    @ExceptionHandler(OrderException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleStockMovementException( OrderException ex ){
        return new ApiErrors(ex.getMessage());
    }  
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodNotValidException( MethodArgumentNotValidException ex ){
        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiErrors(errors);
    }
}
