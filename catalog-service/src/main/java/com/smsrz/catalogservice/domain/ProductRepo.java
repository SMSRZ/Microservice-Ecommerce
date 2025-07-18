package com.smsrz.catalogservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ProductRepo extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity > findByCode(String code);

}
