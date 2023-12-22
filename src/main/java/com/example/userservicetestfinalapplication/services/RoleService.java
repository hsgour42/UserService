package com.example.userservicetestfinalapplication.services;

import com.example.userservicetestfinalapplication.exceptions.RoleAlreadyAvailableException;
import com.example.userservicetestfinalapplication.models.Role;
import com.example.userservicetestfinalapplication.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    @Autowired
    public RoleService(
            RoleRepository roleRepository
    ){
        this.roleRepository = roleRepository;
    }
    public Role createRoles(String requestRole) throws Exception {
        Optional<Role> optionalRole = roleRepository.findByRole(requestRole);
        if(optionalRole.isPresent()){
            throw new RoleAlreadyAvailableException(requestRole + " role is already available");
        }
        Role savedRole = new Role();
        savedRole.setRole(requestRole);

        return roleRepository.save(savedRole);
    }

}
