package com.example.userservicetestfinalapplication.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseModel{
    private String name;
    private String email;
    private String password;
    @ManyToMany
    private Set<Role> roles = new HashSet<>();
}
