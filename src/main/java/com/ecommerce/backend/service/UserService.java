package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.MyUserRepository;
import com.ecommerce.backend.repository.RoleRepository;
import com.ecommerce.backend.service.errors.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    MyUserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;



//    public User save(User user) {
//        return userRepository.saveAndFlush(user);
//    }
//
//    public User update(User user) {
//        return userRepository.save(user);
//    }
//
//    public User find(String userName) {
//        return userRepository.findOneByUsername(userName);
//    }

//    public Optional<User> findById(Long id) {
//        return userRepository.findById(id);
//    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        return null;
    }

    public User registerNewUserAccount(final User account) {
        if (emailExists(account.getEmail())){
            throw new UserAlreadyExistException("There is an account with that email address: " + account.getEmail());
        }
        final User user = new User();

        user.setFirstName(account.getFirstName());
        user.setLastName(account.getLastName());
        user.setPassword((account.getPassword()));
        user.setEmail(account.getEmail());
        user.setUsername(account.getUsername());
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return userRepository.save(user);
    }

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }


    public void saveRegisteredUser(final User user) {
        userRepository.save(user);
    }

    public User  validateUser(String email,String password) throws NoSuchElementException {
        if (email!= null) email= email.toLowerCase();
        User user = userRepository.findByEmailAndPassword(email,password);
        System.out.println(user);
        return user;
    }


}
