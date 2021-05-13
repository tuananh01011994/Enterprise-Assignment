package com.ecommerce.backend.web.controllers;

import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.*;
import com.ecommerce.backend.security.ActiveSessionList;
import com.ecommerce.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Null;
import java.util.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class UserController {

    @Autowired
    MyUserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MyProductRepository myProductRepository;

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private MyOrderRepository myOrderRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getAuthorities());
        return ResponseEntity.accepted().body(userRepository.findAll());
    }

//    @PostMapping("/basketItemAdd")
//    public ResponseEntity<Map<String, String>> createItemBasket(@RequestParam("uId") long userId,@RequestParam("pId") long productId){
//        Optional<Order> order = myOrderRepository.findByUserAndProduct(userId, productId);
//        Order data = new Order();
//        if (order.isPresent()){
//            data = (Order) order;
//
//        }
//        Map<String,String> map = new HashMap<>();
//        map.put("message","Add item to basket succesfully");
//        return new ResponseEntity<>(map, HttpStatus.OK);
//    }

//    @DeleteMapping("/basketItemRemove")
//    public ResponseEntity<Map<String, String>> removeItemBasket(@RequestParam("userId") long uId, @RequestParam("productId") long pId){
//
//    }

    @GetMapping("/test")
    public ModelAndView test(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    //todo: validate email
    @PostMapping("/regular/user")
    public ResponseEntity<Map<String,String>> registerNewRegularUser(@Valid @RequestBody User account, final HttpServletRequest request) {
        userService.isValidEmail(account.getEmail());
        userService.registerNewRegularUserAccount(account);
        Map<String,String> map = new HashMap<>();
        map.put("message","Register successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }




    @GetMapping("/users/{id}")
    public  ResponseEntity<User> getUser(@PathVariable("id") long userID){
            User user = userRepository.findByID(userID);
            return  ResponseEntity.accepted().body(user);
    }

    //todo: validate email
    @PutMapping("/changeemail/{id}")
    public  ResponseEntity<Map<String,String>> updateUserEmail(@PathVariable("id") long userID,@RequestParam("new_email") String newEmail){
        userService.isValidEmail(newEmail);
        User user = userRepository.findByID(userID);
        user.setEmail(newEmail);
        userRepository.save(user);
        Map<String,String> map = new HashMap<>();
        map.put("message","Change your email successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/changepassword/{id}")
    public  ResponseEntity<Map<String,String>> updateUserPassword(@PathVariable("id") long userID,@RequestParam("new_password") String newPassword){
        User user = userRepository.findByID(userID);
        user.setPassword(newPassword);
        userRepository.save(user);
        Map<String,String> map = new HashMap<>();
        map.put("message","Change your password successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> loginUser(@RequestParam("email") final String email,
                                                        @RequestParam("password") final String password){
            Map<String,String> map = new HashMap<>();
            try {
                userService.validateUser(email,password);

                map.put("message","Login successfully");
            } catch (NoSuchElementException e){
                map.put("message","Validate unsuccessfully");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String,String>> deleteUser(@PathVariable("id") long userID) {

        User user = userRepository.findByID(userID);
        userRepository.delete(user);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Delete successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

//    @PostMapping("/orderCreate")
//    public

    public String getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = null;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }



}
