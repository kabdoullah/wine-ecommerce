package com.wine.ecommerce.cart;

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
@Table(name = "carts")
public class Cart extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @NotBlank
    @Column(name = "status", nullable = false)
    private String status;
    
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<CartItem> items = new HashSet<>();

    // Helper methods
    public Integer getTotalAmountCents() {
        return items.stream()
                .mapToInt(CartItem::getPriceCents)
                .sum();
    }
    
    public Double getTotalAmountEuros() {
        return getTotalAmountCents() / 100.0;
    }
    
    public Integer getTotalItems() {
        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
    
    public void addItem(CartItem item) {
        item.setCart(this);
        this.items.add(item);
    }
    
    public void removeItem(CartItem item) {
        this.items.remove(item);
        item.setCart(null);
    }
    
    public void clearItems() {
        this.items.clear();
    }
}