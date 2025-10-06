package com.wine.ecommerce.promotion;

import com.wine.ecommerce.core.BaseEntity;
import com.wine.ecommerce.product.ProductPromotion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "promotions")
public class Promotion extends BaseEntity {
    
    @NotBlank
    @Size(max = 100)
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "discount_percentage")
    private Double discountPercentage;
    
    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    
    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductPromotion> productPromotions = new HashSet<>();


    // Helper methods
    public boolean isActive() {
        LocalDate now = LocalDate.now();
        return !now.isBefore(startDate) && !now.isAfter(endDate);
    }
    
    public boolean isUpcoming() {
        return LocalDate.now().isBefore(startDate);
    }
    
    public boolean isExpired() {
        return LocalDate.now().isAfter(endDate);
    }
}