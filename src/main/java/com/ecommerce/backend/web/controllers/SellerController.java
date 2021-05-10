package com.ecommerce.backend.web.controllers;

import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.entity.Store;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.MyUserRepository;
import com.ecommerce.backend.service.ProductService;
import com.ecommerce.backend.service.StoreService;
import com.ecommerce.backend.service.UserService;
import com.ecommerce.backend.service.errors.StoreNameAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/seller")
public class SellerController {
    @Autowired
    private UserService userService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private MyUserRepository myUserRepository;

    @PostMapping("/stores/{id}")
    public ResponseEntity<Map<String,String>> addStore(@Valid @RequestParam(name="store_name") String storeName, @PathVariable("id") Long userID){

//        if (storeService.isStoreNameExists(storeName)){
//            throw new StoreNameAlreadyExists();
//        }
        Store store = storeService.registerNewStore(storeName);
        User user = myUserRepository.findByID(userID);
        store.setUser(user);
        user.setStore(store);
        myUserRepository.save(user);
        System.out.println(user.getStore());
        Map<String,String> map = new HashMap<>();
        map.put("message","Add store successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

//    @PostMapping("/productAdd/{id}")
//    public ResponseEntity<Map<String, String>> addProduct(@Valid @RequestParam(name="product_name") String prodName, @PathVariable(""))

    @PostMapping("user")
    public ResponseEntity<Map<String,String>> registerNewSellerUser(@Valid @RequestBody User account, final HttpServletRequest request) {
        userService.registerNewSellerUserAccount(account);
        Map<String,String> map = new HashMap<>();
        map.put("message","Register successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
