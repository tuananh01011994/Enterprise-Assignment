package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.entity.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class MyStoreRepository  {

    @Autowired
    StoreRepository storeRepository;

    MyStoreRepository(){}

    public Store findByName(String name){
        return storeRepository.findByStoreName(name).orElseThrow(() -> new NoSuchElementException());
    }

    public Store save(Store store){
        return storeRepository.save(store);
    };

    public Store findByStoreId(long id){
        return storeRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }

    public void delete(Store store) {
        storeRepository.delete(store);
    }

}
