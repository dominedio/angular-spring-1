package com.springcrud.user.exceptions;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
 
@ControllerAdvice
public class Errorcatch extends ResponseEntityExceptionHandler {
 
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
             
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", new Date());
        responseBody.put("status", status.value());
         
        List<String> errors = ex.getBindingResult().getFieldErrors()
            .stream()
            .map(x -> x.getDefaultMessage())
            .collect(Collectors.toList());
         
        responseBody.put("errors", errors);
         
        return new ResponseEntity<>(responseBody, headers, status);
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleMethodNoDataFound(ResourceNotFoundException ex, WebRequest request) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("timestamp", new Date());
		responseBody.put("status", HttpStatus.NOT_FOUND.value());
		responseBody.put("errors", ex.getMessage());
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
	}
    
    
    
    
 
}
