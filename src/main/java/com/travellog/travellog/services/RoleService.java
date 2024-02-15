package com.travellog.travellog.services;

import com.travellog.travellog.configurations.ConversionConfiguration;
import com.travellog.travellog.dtos.CreateRoleDto;
import com.travellog.travellog.dtos.RoleDetailDto;
import com.travellog.travellog.models.Role;
import com.travellog.travellog.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final ConversionConfiguration conversionConfiguration;

    @Autowired
    public RoleService(RoleRepository roleRepository, ConversionConfiguration conversionConfiguration) {
        this.roleRepository = roleRepository;
        this.conversionConfiguration = conversionConfiguration;
    }

    public RoleDetailDto createRole(CreateRoleDto createRoleDto) {
        Role role = conversionConfiguration.convert(createRoleDto, Role.class);
        Role savedRole = roleRepository.save(role);
        return new RoleDetailDto(savedRole.getId(), savedRole.getRoleName());
    }
}
