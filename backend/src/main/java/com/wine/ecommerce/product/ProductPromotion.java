package com.wine.ecommerce.product;

import com.wine.ecommerce.core.BaseEntity;
import com.wine.ecommerce.promotion.Promotion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "product_promotions")
public class ProductPromotion extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;
    
    @Column(name = "start_date")
    private LocalDate startDate;
    
    @Column(name = "end_date")
    private LocalDate endDate;
    
    @Column(name = "specific_discount")
    private Double specificDiscount;

    // Helper methods
    public boolean isActive() {
        LocalDate now = LocalDate.now();
        LocalDate effectiveStartDate = startDate != null ? startDate : promotion.getStartDate();
        LocalDate effectiveEndDate = endDate != null ? endDate : promotion.getEndDate();
        return !now.isBefore(effectiveStartDate) && !now.isAfter(effectiveEndDate);
    }
    
    public Double getEffectiveDiscount() {
        return specificDiscount != null ? specificDiscount : promotion.getDiscountPercentage();
    }
}