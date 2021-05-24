package com.ecommerce.backend.web.controllers;

import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.RoleRepository;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.security.Principal;
import java.util.*;

@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;


    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String getCurrentUserAuthentication() throws JsonProcessingException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        Authentication principal = SecurityContextHolder.getContext().getAuthentication();

        String userName = null;

        String user;
        ObjectMapper mapper = new ObjectMapper();

        if (principal instanceof UserDetails) {
            user = mapper.writeValueAsString(principal);
            System.out.println(user);
        } else {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            auth.setAuthenticated(false);

            user = mapper.writeValueAsString(auth);
            System.out.println(user);
        }
        return user;
    }
    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
    @GetMapping("/product-detail")
    public String getProductDetailsPage(){
        return "product-details";
    }
    @GetMapping("/seller-product")
    public String getSellerPage(){
        return "seller-products";
    }
    @GetMapping("/cart")
    public String getDisplayCartPage(){
        return "display-cart";
    }
    @GetMapping("/profile")
    public String getProfilePage(){
        return "user-profile";
    }
    @GetMapping("/register")
    public String getRegisterPage(){
        return "register";
    }
    @GetMapping("/product-list")
    public String getProductList(){
        return "product-list";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        if (isAuthenticated()) {
            return "redirect:home";
        }
        return "login";
    }

    @GetMapping("/home")
    public String getHomePage(){
        return "home";
    }

    @GetMapping("/getID")
    @ResponseBody
    public String getCurrentUserId() throws JsonProcessingException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ObjectMapper mapper = new ObjectMapper();
        if (principal instanceof UserDetails) {
            User user = userRepository.findByEmail(((UserDetails) principal).
                    getUsername()).orElseThrow(() -> new NoSuchElementException());
            return user.getId().toString();
        }
        return "anonymous user";
    }

    @GetMapping("/getCurrentUser")
    @ResponseBody
    public String getCurrentUser() throws JsonProcessingException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ObjectMapper mapper = new ObjectMapper();
        if (principal instanceof UserDetails) {
            User user = userRepository.findByEmail(((UserDetails) principal).
                    getUsername()).orElseThrow(() -> new NoSuchElementException());


            return mapper.writeValueAsString(user);
        }
        return "anonymous user";

    }




    @GetMapping("/admin")
    public String getAdminPage(){

        return "admin-page";

    }

    @GetMapping("/access-denied")
    public String get403Page(){

        return "access-denied";

    }



}
