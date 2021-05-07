package com.ecommerce.backend.test;

import com.ecommerce.backend.entity.Role;
import com.ecommerce.backend.repository.RoleRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class test {
    @Autowired
    RoleRepository roleRepository;
    @Test
    public void whenSaved_thenFindsByName() {
        Role role = roleRepository.findByName("ROLE_USER");
        System.out.println(role);

    }
}
