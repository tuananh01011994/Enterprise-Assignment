package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByName(String name);
}
