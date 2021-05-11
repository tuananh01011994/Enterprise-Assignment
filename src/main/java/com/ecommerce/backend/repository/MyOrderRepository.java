package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyOrderRepository {
    @Autowired
    private OrderRepository orderRepository;

    public Order save(Order order){return orderRepository.save(order);}
    public void delete(Order order){orderRepository.delete(order);}
    public Optional<Order> findByUserAndProduct(long uId, long pId){return orderRepository.findByUserAndProduct(uId, pId);}


}
