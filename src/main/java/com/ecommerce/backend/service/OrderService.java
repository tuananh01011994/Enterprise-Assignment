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

    public Order registerNewOrder(long userId, long productId){
        final Order order = new Order();
        User user = new User();
        Product product = new Product();

        order.setUser(myUserRepository.findByID(userId));
        order.setProduct(myProductRepository.findProductById(productId));
        return myOrderRepository.save(order);
    }

}
