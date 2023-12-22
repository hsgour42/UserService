package com.example.userservicetestfinalapplication.services;

import com.example.userservicetestfinalapplication.dtos.UserResponseDto;
import com.example.userservicetestfinalapplication.models.Role;
import com.example.userservicetestfinalapplication.models.User;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public interface UserService {
    UserResponseDto getUserDetails(UUID id) throws Exception;

    UserResponseDto setUserRoles(UUID id , Set<Role> roles) throws Exception;
}
