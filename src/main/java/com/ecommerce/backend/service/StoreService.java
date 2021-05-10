package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Store;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.MyStoreRepository;
import com.ecommerce.backend.repository.MyUserRepository;
import com.ecommerce.backend.service.errors.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.NoSuchElementException;

@Service
@Transactional
public class StoreService {


    @Autowired
    MyStoreRepository myStoreRepository;

    @Autowired
    MyUserRepository myUserRepository;

    public boolean isStoreNameExists(String name){
        return myStoreRepository.findByName(name) !=null;
    }

    public Store registerNewStore(String storeName)  {

        final Store store = new Store();
        store.setStoreName(storeName);
        return myStoreRepository.save(store);
    }

}
