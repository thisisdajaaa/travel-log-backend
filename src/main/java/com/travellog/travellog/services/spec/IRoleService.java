package com.travellog.travellog.services.spec;

import com.travellog.travellog.dtos.CreateRoleDto;
import com.travellog.travellog.dtos.RoleDetailDto;

public interface IRoleService {
    RoleDetailDto createRole(CreateRoleDto createRoleDto);

    boolean isRoleListEmpty();
}
