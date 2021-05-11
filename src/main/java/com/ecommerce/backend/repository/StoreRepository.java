package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByStoreNameContaining(String name);
    Optional<Store> findById(long id);
    List<Store> findByUser_Id(long id);
}
