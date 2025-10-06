package com.wine.ecommerce.user.mappers;

import com.wine.ecommerce.user.entities.Role;
import com.wine.ecommerce.user.entities.User;
import com.wine.ecommerce.user.dto.*;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {RoleMapper.class},
    builder = @Builder(disableBuilder = true)
)
public interface UserMapper {
    
    UserResponseDto toResponseDto(User user);
    
    @Mapping(target = "roleNames", source = "roles", qualifiedByName = "rolesToRoleNames")
    UserSummaryDto toSummaryDto(User user);
    
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "carts", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(CreateUserRequest request);
    
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "carts", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateUserRequest request, @MappingTarget User user);
    
    @Named("rolesToRoleNames")
    default Set<String> rolesToRoleNames(Set<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(role -> role.getName().getDisplayName())
                .collect(Collectors.toSet());
    }
}