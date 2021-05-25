package com.ecommerce.backend.spring;

import com.ecommerce.backend.entity.Privilege;
import com.ecommerce.backend.entity.Role;
import com.ecommerce.backend.entity.Store;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.MyStoreRepository;
import com.ecommerce.backend.repository.PrivilegeRepository;
import com.ecommerce.backend.repository.RoleRepository;
import com.ecommerce.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class  SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;
    @Autowired
    private PrivilegeRepository privilegeRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MyStoreRepository storeRepository;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup) {
            return;
        }

        // == create initial privileges
        final Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        final Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        final Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");
        final Privilege shopPrivilege = createPrivilegeIfNotFound("SHOP_PRIVILEGE");
        final Privilege userPrivilege = createPrivilegeIfNotFound("USER_PRIVILEGE");

        // == create initial roles
        final List<Privilege> adminPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
        final List<Privilege> userPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, passwordPrivilege,userPrivilege));
        final List<Privilege> sellerPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, shopPrivilege));

        final Role sellerRole = createRoleIfNotFound("ROLE_SELLER",sellerPrivileges);
        final Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        final Role userRole = createRoleIfNotFound("ROLE_USER", userPrivileges);

        createUserIfNotFound("admin@test.com", "Admin", "Test", "test", new ArrayList<>(Arrays.asList(adminRole)));
        User seller1 = createUserIfNotFound("seller@test.com","Hera", "The Seller", "test", new ArrayList<>(Arrays.asList(sellerRole)));
        User seller2 =  createUserIfNotFound("seller1@test.com","Tommy", "The Seller", "test", new ArrayList<>(Arrays.asList(sellerRole)));


        createUserIfNotFound("user@test.com","User", "Test", "test", new ArrayList<>(Arrays.asList(userRole)));


        alreadySetup = true;
    }

    @Transactional
    User createUserIfNotFound( final String email , final String firstName, final String lastName, final String password, final Collection<Role> roles) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            user = new User();

            user.setUsername(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword((password));
            user.setEmail(email);
        }
        user.setRoles(roles);
        user = userRepository.save(user);
        return user;
    }


    @Transactional
     Store createStoreIfNotFound(final Long id,String storeName){
        Store store = new Store();
        store.setStoreName(storeName);
        store.setId(id);
        storeRepository.save(store);

        return store;
    }


    private Role createRoleIfNotFound(final String name, List<Privilege> adminPrivileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
        }
        role.setPrivileges(adminPrivileges);
        role = roleRepository.save(role);
        return role;

    }


    @Transactional
    public Privilege createPrivilegeIfNotFound(final String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilege = privilegeRepository.save(privilege);
        }
        return privilege;
    }
}

