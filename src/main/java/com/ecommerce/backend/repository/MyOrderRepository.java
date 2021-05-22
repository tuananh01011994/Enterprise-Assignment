package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.entity.Store;
import com.ecommerce.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MyOrderRepository {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> basketItemGet(long userId, String orderTime){
        return orderRepository.findByUser_IdAndTime(userId, orderTime);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void delete(Order order){
        orderRepository.delete(order);
    }

    public List<Order> findAll() { return orderRepository.findAll();}
}
