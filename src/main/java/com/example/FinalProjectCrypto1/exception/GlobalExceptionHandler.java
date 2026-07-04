package com.example.FinalProjectCrypto1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> manejarNoEncontrado(ResourceNotFoundException ex) {

        Map<String, String> response = new LinkedHashMap<>();
        response.put("mensaje", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, String>> manejarDuplicado(DuplicateResourceException ex) {

        Map<String, String> response = new LinkedHashMap<>();
        response.put("mensaje", ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException ex) {

        Map<String, String> errores = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> manejarGeneral(Exception ex) {

        Map<String, String> response = new LinkedHashMap<>();
        response.put("mensaje", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}