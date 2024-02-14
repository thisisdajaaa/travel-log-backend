package com.travellog.travellog.controllers;

import com.travellog.travellog.dtos.CreateRoleDto;
import com.travellog.travellog.dtos.RoleDetailDto;
import com.travellog.travellog.helpers.ResponseHelper;
import com.travellog.travellog.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final RoleService roleService;


    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<ResponseHelper<RoleDetailDto>> createRole(@Valid @RequestBody CreateRoleDto createRoleDto) {
        return new ResponseEntity<>(
                new ResponseHelper<>(true, "Successfully created role!", roleService.createRole(createRoleDto)),
                HttpStatus.CREATED
        );
    }
}
