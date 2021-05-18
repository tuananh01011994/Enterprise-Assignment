package com.ecommerce.backend.web.controllers;

import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.entity.Store;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.MyProductRepository;
import com.ecommerce.backend.repository.MyStoreRepository;
import com.ecommerce.backend.repository.MyUserRepository;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.ProductService;
import com.ecommerce.backend.service.StoreService;
import com.ecommerce.backend.service.UserService;
import com.ecommerce.backend.service.errors.StoreNameAlreadyExists;
import com.ecommerce.backend.utility.FileUploadUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    @Autowired
    private UserService userService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private MyStoreRepository myStoreRepository;

    @Autowired
    private MyProductRepository myProductRepository;

    @PostMapping("/storeAdd/{id}")
    public ResponseEntity<Map<String,String>> addStore(@Valid @RequestParam(name="store_name") String storeName, @PathVariable("id") Long userID){

        User user = myUserRepository.findByID(userID);


        if(myStoreRepository.existsByUser(myUserRepository.findByID(userID))){
            Map<String,String> map = new HashMap<>();
            map.put("message","User maximum store reach");
            return new ResponseEntity<>(map, HttpStatus.CONFLICT);
        }
        Store store = storeService.registerNewStore(storeName);
        store.setUser(user);
        user.setStore(store);
        myUserRepository.save(user);
        Map<String,String> map = new HashMap<>();
        map.put("message","Add store successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/storeUpdate/{id}")
    public ResponseEntity<Map<String, String>> updateStore(@Valid @RequestBody Store updated, @PathVariable("id") long original){
        Store store = myStoreRepository.findByStoreId(original);
        store.setStoreName(updated.getStoreName());
        myStoreRepository.save(store);
        Map<String, String> map = new HashMap<>();
        map.put("Message", "Update store successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/storeDelete/{id}")
    public ResponseEntity<Map<String, String>> updateStore(@Valid @PathVariable("id") long id){
        Store store = myStoreRepository.findByStoreId(id);
        myStoreRepository.delete(store);
        Map<String, String> map = new HashMap<>();
        map.put("Message", "Delete store successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/storeGet/{id}")
    public ResponseEntity<Store> getStore(@Valid @PathVariable("id") long storeID){
        return ResponseEntity.accepted().body(myStoreRepository.findByStoreId(storeID));
    }

    @GetMapping("/storeGetAll")
    public ResponseEntity<List<Store>> getAllStore(){
        return ResponseEntity.accepted().body(myStoreRepository.findAll());
    }

    @GetMapping("/storeGetName/{key}")
    public ResponseEntity<List<Store>> getByNameStore(@Valid @PathVariable("key") String keyword){
        return ResponseEntity.accepted().body(myStoreRepository.findByName(keyword));
    }

    @GetMapping("/storeGetByUser/{uID}")
    public ResponseEntity<List<Store>> getByUserStore(@Valid @PathVariable("uID") long userID){
        return ResponseEntity.accepted().body(myStoreRepository.findByUser(userID));
    }

    @PostMapping("/productAdd/{id}")
    public ResponseEntity<Map<String, String>> addProduct(@Valid @RequestBody Product inProd, @PathVariable("id") long storeID){
        Map<String, String> map = new HashMap<>();

        Store store = myStoreRepository.findByStoreId(storeID);
        Product product = productService.registerNewProduct(inProd.getProductName(), inProd.getProductPrice(), inProd.getProductDescription(), storeID);
        product.setStore(store);


        myStoreRepository.save(store);
        map.put("Message", "Add product successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @PutMapping("/productUpdate/{id}")
    public ResponseEntity<Map<String, String>> updateProduct(@Valid @RequestBody Product updated, @PathVariable("id") long original, @RequestParam(value = "image",required = false) MultipartFile multipartFile){
        Map<String, String> map = new HashMap<>();

        Product product = myProductRepository.findProductById(original);
        product.setProductName(updated.getProductName());
        product.setProductDescription(updated.getProductDescription());
        product.setQuantity(updated.getQuantity());
        product.setProductPrice(updated.getProductPrice());


        myProductRepository.save(product);
        map.put("Message", "Update Product successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/productUpdate/{id}/photo")
    public ResponseEntity<Map<String, String>>addProductPhoto(@RequestParam(value = "image") MultipartFile multipartFile,  @PathVariable("id")Long productID) {
        Map<String, String> map = new HashMap<>();

        Product product = myProductRepository.findProductById(productID);
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        product.setPhotos(fileName);
        String uploadDir = "/product-photos/" + product.getId();
        try{
            FileUploadUtility.saveFile(uploadDir, fileName, multipartFile);

        } catch (IOException e){
            map.put("message", "Can't add photo to product");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);

        }
        map.put("Message", "Update Product successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/productQuantityUpdate")
    public ResponseEntity<Map<String, String>> updateProductQuantity(@Valid @RequestParam("id") long id, @RequestParam("quantity") int quantity){
        Product product = myProductRepository.findProductById(id);
        product.setQuantity(quantity);
        myProductRepository.save(product);
        Map<String, String> map = new HashMap<>();
        map.put("Message", "Update Product quantity successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/productDelete/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@Valid @PathVariable("id") long id){
        Product product = myProductRepository.findProductById(id);
        myProductRepository.delete(product);
        Map<String, String> map = new HashMap<>();
        map.put("Message", "Delete product successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/productGet/{id}")
    public ResponseEntity<Product> getProduct(@Valid @PathVariable("id") long id){
        Product product = myProductRepository.findProductById(id);
        return  ResponseEntity.accepted().body(product);
    }

    @GetMapping("/productGetAll")
    public ResponseEntity<List<Product>> getAllProduct(){
        return ResponseEntity.accepted().body(myProductRepository.findProductsThatContain(""));
    }

    @GetMapping("/productGetByKeyword/{key}")
    public ResponseEntity<List<Product>> getByKeyProduct(@Valid @PathVariable("key") String keyword){
        return ResponseEntity.accepted().body(myProductRepository.findProductsThatContain(keyword));
    }

    @GetMapping("/productGetByDetail/{key}")
    public ResponseEntity<List<Product>> getByDetailProduct(@Valid @PathVariable("key") String keyword){
        return ResponseEntity.accepted().body(myProductRepository.findProductByDetail(keyword));
    }

    @GetMapping("/productGetByStore/{id}")
    public ResponseEntity<List<Product>> getByStoreProduct(@Valid @PathVariable("id") long id){
        return ResponseEntity.accepted().body(myProductRepository.findProductByStore(id));
    }



    @PostMapping("user")
    public ResponseEntity<Map<String,String>> registerNewSellerUser(@Valid @RequestBody User account, final HttpServletRequest request) {
        userService.registerNewSellerUserAccount(account);
        Map<String,String> map = new HashMap<>();
        map.put("message","Register successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
