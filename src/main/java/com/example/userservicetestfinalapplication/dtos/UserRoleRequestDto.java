package com.example.userservicetestfinalapplication.dtos;

import com.example.userservicetestfinalapplication.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserRoleRequestDto {
    private Set<Role> roles;
}
