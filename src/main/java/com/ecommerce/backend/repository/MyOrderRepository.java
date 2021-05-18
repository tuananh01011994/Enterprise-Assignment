package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MyOrderRepository {
    @Autowired
    private OrderRepository orderRepository;

    public Order save(Order order){return orderRepository.save(order);}
    public void delete(Order order){orderRepository.delete(order);}
    public Order findByUserAndProduct(User user, Product product){return orderRepository.findByUserAndProduct(user, product);}
    public boolean existByUserAndProduct(User user, Product product){return orderRepository.existsByUserAndProduct(user, product);}
    public List<Order> findByUser(User user){return orderRepository.findByUser(user);}
    public Order findById(long id){
        return orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }
    public List<Order> findAll(){return orderRepository.findAll();}
}
