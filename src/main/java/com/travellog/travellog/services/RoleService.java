package com.travellog.travellog.services;

import com.travellog.travellog.dtos.CreateRoleDto;
import com.travellog.travellog.dtos.RoleDetailDto;

public interface RoleService {
    RoleDetailDto createRole(CreateRoleDto createRoleDto);
}
