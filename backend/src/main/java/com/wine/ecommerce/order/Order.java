package com.wine.ecommerce.order;

import com.wine.ecommerce.core.BaseEntity;
import com.wine.ecommerce.user.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    // Getters and Setters
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @NotBlank
    @Column(name = "order_number", unique = true, nullable = false)
    private String orderNumber;
    
    @NotBlank
    @Column(name = "status", nullable = false)
    private String status;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderItem> items = new HashSet<>();

    // Helper methods
    public Integer getTotalAmountCents() {
        return items.stream()
                .mapToInt(OrderItem::getPriceCents)
                .sum();
    }
    
    public Double getTotalAmountEuros() {
        return getTotalAmountCents() / 100.0;
    }
    
    public Integer getTotalItems() {
        return items.stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();
    }
}