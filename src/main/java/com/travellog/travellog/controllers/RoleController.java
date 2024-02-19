package com.travellog.travellog.controllers;

import com.travellog.travellog.dtos.role.CreateRoleDto;
import com.travellog.travellog.dtos.role.RoleDetailDto;
import com.travellog.travellog.helpers.ResponseHelper;
import com.travellog.travellog.services.spec.IRoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<ResponseHelper.CustomResponse<RoleDetailDto>> createRole(
            @Valid @RequestBody CreateRoleDto createRoleDto) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully created role!",
                        roleService.createRole(createRoleDto)),
                HttpStatus.CREATED);
    }
}
