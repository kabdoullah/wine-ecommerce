package com.wine.ecommerce.user.mappers;

import com.wine.ecommerce.user.dto.UserRoleDto;
import com.wine.ecommerce.user.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    
    UserRoleDto toDto(Role role);
    
    Role toEntity(UserRoleDto roleDto);
    
    List<UserRoleDto> toDtoList(List<Role> roles);
}