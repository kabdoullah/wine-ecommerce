package com.wine.ecommerce.category;

import com.wine.ecommerce.core.BaseEntity;
import com.wine.ecommerce.product.ProductCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    // Getters and Setters
    @NotBlank
    @Size(max = 100)
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotBlank
    @Size(max = 120)
    @Column(name = "slug", nullable = false, unique = true)
    private String slug;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductCategory> productCategories = new HashSet<>();

}