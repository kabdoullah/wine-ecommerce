package com.wine.ecommerce.product;

import com.wine.ecommerce.core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "products")
public class Product extends BaseEntity {
    
    @NotBlank
    @Size(max = 50)
    @Column(name = "sku", nullable = false, unique = true)
    private String sku;

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
    
    @Size(max = 255)
    @Column(name = "short_description")
    private String shortDescription;
    
    @Size(max = 100)
    @Column(name = "producer")
    private String producer;
    
    @Size(max = 100)
    @Column(name = "region")
    private String region;
    
    @Column(name = "vintage")
    private Integer vintage;
    
    @Column(name = "alcohol")
    private Double alcohol;
    
    @Column(name = "volume_ml")
    private Integer volumeMl = 750;
    
    @Size(max = 20)
    @Column(name = "format")
    private String format;
    
    @NotNull
    @Positive
    @Column(name = "base_price_cents", nullable = false)
    private Integer basePriceCents;
    
    @NotNull
    @Positive
    @Column(name = "current_price_cents", nullable = false)
    private Integer currentPriceCents;

    
    @Column(name = "stock")
    private Integer stock = 0;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductCategory> productCategories = new HashSet<>();
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductPromotion> productPromotions = new HashSet<>();
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<com.wine.ecommerce.review.Review> reviews = new HashSet<>();


}