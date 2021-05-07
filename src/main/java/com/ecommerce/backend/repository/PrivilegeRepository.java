package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
//    @Query("select c from Privilege c where c.name = ?1")
    Privilege findByName(String name);
}
