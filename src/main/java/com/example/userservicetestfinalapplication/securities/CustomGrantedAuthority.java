package com.example.userservicetestfinalapplication.securities;

import com.example.userservicetestfinalapplication.models.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@NoArgsConstructor
@JsonDeserialize(as = CustomGrantedAuthority.class)
public class CustomGrantedAuthority implements GrantedAuthority {
    private  Role role;

    public CustomGrantedAuthority(Role role) {
        this.role = role;
    }

    @JsonIgnore
    @Override
    public String getAuthority() {
        return role.getRole();
    }
}
