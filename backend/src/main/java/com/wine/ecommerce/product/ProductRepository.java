package com.wine.ecommerce.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    
    Optional<Product> findBySku(String sku);
    
    Page<Product> findByStockGreaterThan(Integer stock, Pageable pageable);
    
    Optional<Product> findByIdAndStockGreaterThan(UUID id, Integer stock);
    
    Page<Product> findByNameContainingIgnoreCaseOrProducerContainingIgnoreCaseAndStockGreaterThan(
            String name, String producer, Integer stock, Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE " +
           "(:region IS NULL OR LOWER(p.region) LIKE LOWER(CONCAT('%', :region, '%'))) AND " +
           "(:producer IS NULL OR LOWER(p.producer) LIKE LOWER(CONCAT('%', :producer, '%'))) AND " +
           "p.stock > 0")
    Page<Product> findByFilters(@Param("region") String region, 
                               @Param("producer") String producer, 
                               Pageable pageable);
}