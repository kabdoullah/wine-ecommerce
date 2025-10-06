package com.wine.ecommerce.product;

import com.wine.ecommerce.category.Category;
import com.wine.ecommerce.core.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "product_categories")
public class ProductCategory extends BaseEntity {

    // Getters and Setters
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    @Column(name = "date_added")
    private LocalDate dateAdded;
    
    @Column(name = "is_primary")
    private Boolean isPrimary = false;

}