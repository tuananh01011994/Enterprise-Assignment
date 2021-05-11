package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.MyStoreRepository;
import com.ecommerce.backend.repository.MyUserRepository;
import com.ecommerce.backend.repository.RoleRepository;
import com.ecommerce.backend.service.errors.InvalidFormatException;
import com.ecommerce.backend.service.errors.UserAlreadyExistException;
import com.ecommerce.backend.service.errors.EmailNotExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserService {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);
    @Autowired
    MyUserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    @Autowired
    MyStoreRepository myStoreRepository;


    public void LoginRegularUserAccount(final User account) {
        if (emailNotExists(account.getEmail())) {
            throw new EmailNotExists();
        }
    }


    public User registerNewRegularUserAccount(final User account) {
        if(emailExists(account.getEmail())){
            throw new UserAlreadyExistException();
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

    public User registerNewSellerUserAccount(final User account) {
        if (emailExists(account.getEmail())){
            throw new UserAlreadyExistException();
        }
        final User user = new User();

        user.setFirstName(account.getFirstName());
        user.setLastName(account.getLastName());
        user.setPassword((account.getPassword()));
        user.setEmail(account.getEmail());
        user.setUsername(account.getUsername());


        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_SELLER")));

        return userRepository.save(user);
    }

    private boolean emailExists(final String email) {
        try{userRepository.findByEmail(email);}
        catch (EmailNotExists e){return false;}
        return true;
    }

    private boolean emailNotExists(final String email) {
        try{userRepository.findByEmail(email);}
        catch (EmailNotExists e){return true;}
        return false;
    }


    public void saveRegisteredUser(final User user) {
        userRepository.save(user);
    }

    public User  validateUser(String email,String password) {
        email= email.toLowerCase();
        isValidEmail(email);
        return userRepository.findByEmailAndPassword(email,password);
    }
    public boolean isValidEmail(final String username) {
        if (!validateEmail(username)){
            throw new InvalidFormatException();
        }
        return true;
    }

    private boolean validateEmail(final String email) {
        Matcher matcher = PATTERN.matcher(email);
        return matcher.matches();
    }
}
