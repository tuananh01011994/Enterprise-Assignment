package com.ecommerce.backend.service.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value = UserAlreadyExistException.class)
    public ResponseEntity<Object> exception( UserAlreadyExistException exception) {
        Map<String,String> map = new HashMap<>();
        map.put("message","There is already a user registered with this email!");
        return new ResponseEntity<>(map, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<Object> exception( NoSuchElementException exception) {
        Map<String,String> map = new HashMap<>();
        map.put("message","There is no such element in the database!");
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidFormatException.class)
    public ResponseEntity<Object> exception( InvalidFormatException exception) {
        Map<String,String> map = new HashMap<>();
        map.put("message","The data you entered doesn't have the correct format!");
        return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = StoreNameAlreadyExists.class)
    public ResponseEntity<Object> exception( StoreNameAlreadyExists exception) {
        Map<String,String> map = new HashMap<>();
        map.put("message","Store name already exists!");
        return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
    }
}
