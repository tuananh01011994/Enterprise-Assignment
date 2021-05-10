package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.entity.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MyStoreRepository  {

    @Autowired
    StoreRepository storeRepository;

    MyStoreRepository(){}

    public List<Store> findByName(String name){
        List<Store> result = storeRepository.findByStoreNameContaining(name);
        if(result.isEmpty()) throw new NoSuchElementException();
        return storeRepository.findByStoreNameContaining(name);
    }

    public List<Store> findByUser(long uID){
        return storeRepository.findByUser_Id(uID);
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

    public List<Store> findAll(){
        return storeRepository.findAll();
    }
}
