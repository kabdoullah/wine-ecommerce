package com.wine.ecommerce.user.entities;

import com.wine.ecommerce.core.BaseEntity;
import com.wine.ecommerce.cart.Cart;
import com.wine.ecommerce.order.Order;
import com.wine.ecommerce.review.Review;
import com.wine.ecommerce.user.enums.UserRole;
import com.wine.ecommerce.user.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    
    @NotBlank
    @Email
    @Size(max = 100)
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @NotBlank
    @Size(min = 6, max = 120)
    @Column(name = "password", nullable = false)
    private String password;
    
    @NotBlank
    @Size(max = 50)
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @NotBlank
    @Size(max = 50)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Size(max = 20)
    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status = UserStatus.ACTIVE;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Address> addresses = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Cart> carts = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Order> orders = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Review> reviews = new HashSet<>();

    public void addRole(Role role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
    }

    public void removeRole(Role role) {
        if (roles != null) {
            roles.remove(role);
        }
    }

    public boolean hasRole(UserRole role) {
        return roles.stream().anyMatch(r -> r.getName() == role);
    }

    public boolean isActive() {
        return UserStatus.ACTIVE.equals(this.status);
    }

    public boolean hasAnyRole(UserRole... rolesToCheck) {
        return roles.stream()
                .anyMatch(role -> java.util.Arrays.asList(rolesToCheck).contains(role.getName()));
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}