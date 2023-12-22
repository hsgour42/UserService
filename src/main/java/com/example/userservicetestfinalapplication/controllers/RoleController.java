package com.example.userservicetestfinalapplication.controllers;

import com.example.userservicetestfinalapplication.dtos.RolesRequestDto;
import com.example.userservicetestfinalapplication.models.Role;
import com.example.userservicetestfinalapplication.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;
    @Autowired
    public RoleController(
            RoleService roleService
    ){
        this.roleService = roleService;
    }
    @PostMapping
    public ResponseEntity<Role> createRoles(
            @RequestBody RolesRequestDto rolesRequestDto
    ) throws Exception{
          Role roles = roleService.createRoles(rolesRequestDto.getRole());
          return new ResponseEntity<>(roles , HttpStatus.CREATED);
    }

}
