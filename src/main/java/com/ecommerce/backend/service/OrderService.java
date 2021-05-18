package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.entity.Store;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.MyOrderRepository;
import com.ecommerce.backend.repository.MyProductRepository;
import com.ecommerce.backend.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderService {

    @Autowired
    MyUserRepository myUserRepository;

    @Autowired
    MyProductRepository myProductRepository;

    @Autowired
    MyOrderRepository myOrderRepository;



}
