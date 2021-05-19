package com.ecommerce.backend.web.controllers;

import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.*;
import com.ecommerce.backend.security.ActiveSessionList;
import com.ecommerce.backend.service.UserService;
import com.ecommerce.backend.utility.FileUploadUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Null;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

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

    @PostMapping("/basketItemAdd")
    public ResponseEntity<Map<String, String>> createItemBasket(@Valid @RequestParam("uId") long userId,@RequestParam("pId") long productId){
        User user = myUserRepository.findByID(userId);
        Product product = myProductRepository.findProductById(productId);
        Order order = new Order();

        if(myOrderRepository.existByUserAndProduct(user, product)){
            order = myOrderRepository.findByUserAndProduct(user, product);
            order.setProductCount(order.getProductCount() + 1);
            myOrderRepository.save(order);
            Map<String,String> map = new HashMap<>();
            map.put("message","Add existed item to basket succesfully");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

        order.setUser(user);
        order.setProduct(product);
        order.setProductCount(1);
        myOrderRepository.save(order);
        Map<String,String> map = new HashMap<>();
        map.put("message","Add item to basket succesfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/basketItemUpdate/{oId}")
    public ResponseEntity<Map<String, String>> updateBasketItemUpdate(@Valid @RequestParam("quantity") int quantity,
                                                                      @PathVariable("oId") long id){
        Order order = myOrderRepository.findById(231);
        if(quantity == 0){
            myOrderRepository.delete(order);

            Map<String,String> map = new HashMap<>();
            map.put("message","Bakset item remove successfully");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        order.setProductCount(quantity);
        myOrderRepository.save(order);

        Map<String,String> map = new HashMap<>();
        map.put("message","Bakset item quantity update successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/basketItemDelete/{oId}")
    public ResponseEntity<Map<String, String>> deleteBasketItem(@Valid @PathVariable("oId") long id){
        Order order = myOrderRepository.findById(id);
        myOrderRepository.delete(order);
        Map<String,String> map = new HashMap<>();
        map.put("message","Bakset item delete successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/basketDelete/{uId}")
    public ResponseEntity<Map<String, String>> deleteBasket(@Valid @PathVariable("uId") long uId){
        List<Order> orders = myOrderRepository.findByUser(myUserRepository.findByID(uId));
        for (Order order: orders){
            myOrderRepository.delete(order);
        }

        Map<String,String> map = new HashMap<>();
        map.put("message","Basket delete successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/basketGet/{uId}")
    public ResponseEntity<List<Order>> getBasket(@Valid @PathVariable("uId") long uId){
        return ResponseEntity.accepted().body(myOrderRepository.findByUser(myUserRepository.findByID(uId)));
    }

    @GetMapping("/basketGetAll")
    public ResponseEntity<List<Order>> getAllBasket(){
        return ResponseEntity.accepted().body(myOrderRepository.findAll());
    }

    @GetMapping("/basketItemGet/{oId}")
    public ResponseEntity<Order> getBasketItem(@Valid @PathVariable("oId") long id){
        return ResponseEntity.accepted().body(myOrderRepository.findById(id));
    }


    //todo: validate email
    @RequestMapping(value = "/regular/user", method = RequestMethod.POST)
//    @PostMapping("/regular/user")
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

    @PutMapping("/user/{id}/photo")
    public  ResponseEntity<Map<String,String>>  saveUserPhoto(@RequestParam("image") MultipartFile multipartFile,@PathVariable("id") long userID){
        Map<String, String> map = new HashMap<>();

        User user = userRepository.findByID(userID);
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setPhotos(fileName);
        userRepository.save(user);
        String uploadDir = "/user-photos/" + user.getId();
        try{
            FileUploadUtility.saveFile(uploadDir, fileName, multipartFile);

        } catch (IOException e){
            map.put("message", "Can't add photo to user");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);

        }

        map.put("message", "Add photo successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
