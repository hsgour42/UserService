package com.example.userservicetestfinalapplication.dtos;

import com.example.userservicetestfinalapplication.models.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
public class JwsTokenPayload {
    private String email;
    private Set<Role> roles;
    private Date createdAt;
    private Date expiredAt;
}
