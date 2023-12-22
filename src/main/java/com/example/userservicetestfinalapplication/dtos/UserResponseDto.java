package com.example.userservicetestfinalapplication.dtos;

import com.example.userservicetestfinalapplication.models.Role;
import com.example.userservicetestfinalapplication.models.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UserResponseDto {
    private UUID id;
    private String name;
    private String email;
    private Set<Role> roles = new HashSet<>();

    public static UserResponseDto convertModelToDto(User user){
        UserResponseDto response = new UserResponseDto();
         response.setId(user.getId());
         response.setName(user.getName());
         response.setRoles(user.getRoles());
         response.setEmail(user.getEmail());
        return response;
    }
}
