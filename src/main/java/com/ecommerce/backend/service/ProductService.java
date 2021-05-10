package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.entity.Store;
import com.ecommerce.backend.repository.MyProductRepository;
import com.ecommerce.backend.repository.MyStoreRepository;
import com.ecommerce.backend.repository.ProductRepository;
import com.ecommerce.backend.repository.StoreRepository;
import com.ecommerce.backend.service.errors.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class ProductService {



    @Autowired
    ProductRepository productRepository;

    @Autowired
    MyStoreRepository myStoreRepository;

    @Autowired
    MyProductRepository myProductRepository;

    public void saveProduct(Product product){
        System.out.println(product.getProductPrice());
        if(!validatePrice(product.getProductPrice())) {
            throw new InvalidFormatException();
        } else{
            productRepository.save(product);
        }

    }


    private boolean validatePrice(final String price) {
        try {
            Float.parseFloat(price);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public Product registerNewProduct(String productName, String price, String description, long storeId){
        validatePrice(price);
        final Product product = new Product();
        Store store = new Store();
        product.setProductName(productName);
        product.setProductPrice(price);
        product.setProductDescription(description);
        product.setStore(myStoreRepository.findByStoreId(storeId));
        return myProductRepository.save(product);
    }

}
