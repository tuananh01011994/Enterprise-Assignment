package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.entity.Store;
import com.ecommerce.backend.entity.User;
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

    public Product findProductById(long id){
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }
    
    public List<Product> findProductByStore(long id){
        return productRepository.findByStore_Id(id);
    }

    public List<Product> findProductByDetail(String detail){
        List<Product> result = productRepository.findByProductDescriptionContaining(detail);
        if(result.isEmpty()) throw new NoSuchElementException();
        return productRepository.findByProductDescriptionContaining(detail);
    }

    public Product save(Product product){
        return productRepository.save(product);
    };

    public void delete(Product product) {
        productRepository.delete(product);
    }
}
