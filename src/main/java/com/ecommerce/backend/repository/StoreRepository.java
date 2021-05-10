package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByStoreName(String name);
}
