package com.wine.ecommerce.order;

import com.wine.ecommerce.core.BaseEntity;
import com.wine.ecommerce.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Setter
@Getter
public class OrderItem extends BaseEntity {

    // Getters and Setters
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @NotNull
    @Min(1)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @NotNull
    @Column(name = "price_cents", nullable = false)
    private Integer priceCents;

    // Helper methods
    public Double getPriceEuros() {
        return priceCents / 100.0;
    }
    
    public Integer getSubtotalCents() {
        return priceCents * quantity;
    }
    
    public Double getSubtotalEuros() {
        return getSubtotalCents() / 100.0;
    }
}