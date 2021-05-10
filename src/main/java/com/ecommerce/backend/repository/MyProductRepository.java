package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
@Service

public class MyProductRepository {
    @Autowired
    private ProductRepository productRepository;


    public List<Product> findProductsThatContain(String keyword){
        List<Product> results = productRepository.findByProductNameContaining(keyword);
        if (results.isEmpty()) throw new NoSuchElementException();
        return productRepository.findByProductNameContaining(keyword);
    }

    public Product findProductById(String id){
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }
}
