package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.service.errors.NoSuchUserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MyUserRepository{
    @Autowired
    UserRepository userRepository;

    public User findOneByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException());
    }


    public User findByEmailAndPassword(String email, String password) {
       return userRepository.findByEmailAndPassword(email,password).orElseThrow(() -> new NoSuchElementException());
    }

    public void delete(User user) {
        userRepository.delete(user);
    }


    public User findByID(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }

    public boolean existById(long id){
        return userRepository.existsById(id);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User save(User user){
        return userRepository.save(user);
    }


}
