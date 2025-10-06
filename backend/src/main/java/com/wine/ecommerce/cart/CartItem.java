package com.wine.ecommerce.cart;

import com.wine.ecommerce.core.BaseEntity;
import com.wine.ecommerce.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "cart_items")
public class CartItem extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;
    
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

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPriceCents() {
        return priceCents;
    }

    public void setPriceCents(Integer priceCents) {
        this.priceCents = priceCents;
    }
    
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