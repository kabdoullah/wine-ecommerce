package com.wine.ecommerce.user.entities;

import com.wine.ecommerce.core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @NotBlank
    @Size(max = 255)
    @Column(name = "street", nullable = false)
    private String street;
    
    @NotBlank
    @Size(max = 100)
    @Column(name = "city", nullable = false)
    private String city;
    
    @NotBlank
    @Size(max = 20)
    @Column(name = "zipcode", nullable = false)
    private String zipcode;
    
    @NotBlank
    @Size(max = 100)
    @Column(name = "country", nullable = false)
    private String country;


    
    // Helper method
    public String getFullAddress() {
        return String.format("%s, %s %s, %s", street, zipcode, city, country);
    }
}