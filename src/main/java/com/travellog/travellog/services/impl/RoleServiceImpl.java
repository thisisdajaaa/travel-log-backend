package com.travellog.travellog.services.impl;

import com.travellog.travellog.configurations.ConversionConfiguration;
import com.travellog.travellog.dtos.role.CreateRoleDto;
import com.travellog.travellog.dtos.role.RoleDetailDto;
import com.travellog.travellog.models.Role;
import com.travellog.travellog.repositories.IRoleRepository;
import com.travellog.travellog.services.spec.IRoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepository roleRepository;
    private final ConversionConfiguration conversionConfiguration;

    public RoleServiceImpl(IRoleRepository roleRepository, ConversionConfiguration conversionConfiguration) {
        this.roleRepository = roleRepository;
        this.conversionConfiguration = conversionConfiguration;
    }

    @Override
    public RoleDetailDto createRole(CreateRoleDto createRoleDto) {
        Role role = conversionConfiguration.convert(createRoleDto, Role.class);
        Role savedRole = roleRepository.save(role);
        return conversionConfiguration.convert(savedRole, RoleDetailDto.class);
    }

    @Override
    public boolean isRoleListEmpty() {
        return roleRepository.count() == 0;
    }
}
