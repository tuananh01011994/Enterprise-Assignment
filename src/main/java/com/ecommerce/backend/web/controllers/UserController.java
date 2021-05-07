package com.ecommerce.backend.web.controllers;

import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.MyUserRepository;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    MyUserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity<Map<String,String>> registerNewUser(@Valid @RequestBody User account, final HttpServletRequest request) {
        userService.registerNewUserAccount(account);
        Map<String,String> map = new HashMap<>();
        map.put("message","Register successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public  User getUser(@PathVariable("id") long userID){
        try {
            return  userRepository.findByID(userID);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No user with such ID exists");
        }

    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> loginUser(   @RequestParam("email") final String email,
                                                           @RequestParam("password") final String password){
        try {
            userService.validateUser(email,password);
            Map<String,String> map = new HashMap<>();
            map.put("message","Login successfully");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Invalid password/username");
        }
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String,String>> deleteUser(@PathVariable("id") long userID){
        try {
            User user = userRepository.findByID(userID);
            userRepository.delete(user);
            Map<String,String> map = new HashMap<>();
            map.put("message","Delete successfully");
            return  new ResponseEntity<>(map, HttpStatus.OK) ;
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No user with such ID exists");
        }

    }



}
