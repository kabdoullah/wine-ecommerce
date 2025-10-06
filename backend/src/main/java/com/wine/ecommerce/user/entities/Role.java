package com.wine.ecommerce.user.entities;

import com.wine.ecommerce.core.BaseEntity;
import com.wine.ecommerce.user.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true)
    @NotNull
    private UserRole name;

    @NotBlank
    @Size(max = 100)
    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<User> users = new HashSet<>();

    public Role(UserRole name) {
        this.name = name;
        this.displayName = name.getDisplayName();
        this.description = name.getDescription();
        this.isActive = true;
    }

    public String getAuthority() {
        return name.getAuthority();
    }
}