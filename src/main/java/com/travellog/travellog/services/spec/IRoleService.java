package com.travellog.travellog.services.spec;

import com.travellog.travellog.dtos.role.CreateRoleDto;
import com.travellog.travellog.dtos.role.RoleDetailDto;

public interface IRoleService {
    RoleDetailDto createRole(CreateRoleDto createRoleDto);

    boolean isRoleListEmpty();
}
