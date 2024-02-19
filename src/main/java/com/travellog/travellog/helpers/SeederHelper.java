package com.travellog.travellog.helpers;

import com.travellog.travellog.constants.CountryListEnum;
import com.travellog.travellog.constants.RoleEnum;
import com.travellog.travellog.dtos.country.CreateCountryDto;
import com.travellog.travellog.dtos.role.CreateRoleDto;
import com.travellog.travellog.dtos.user.CreateUserDto;
import com.travellog.travellog.services.spec.ICountryService;
import com.travellog.travellog.services.spec.IRoleService;
import com.travellog.travellog.services.spec.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SeederHelper implements CommandLineRunner {

    private final IRoleService roleService;
    private final ICountryService countryService;
    private final IUserService userService;

    public SeederHelper(IRoleService roleService, ICountryService countryService, IUserService userService) {
        this.roleService = roleService;
        this.countryService = countryService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedRolesTable();
        seedUsers();
        seedCountries();
    }

    private void seedRolesTable() {
        if (!roleService.isRoleListEmpty())
            return;

        String[] roleNames = { "ADMIN", "USER" };

        for (String roleName : roleNames) {
            CreateRoleDto createRoleDto = new CreateRoleDto(roleName);
            try {
                roleService.createRole(createRoleDto);
                System.out.println("Seeded " + roleName + " role.");
            } catch (Exception e) {
                System.out.println("Role " + roleName + " already exists or could not be created: " + e.getMessage());
            }
        }
    }

    private void seedCountries() {
        if (!countryService.isCountryListEmpty())
            return;

        for (CountryListEnum countryCode : CountryListEnum.values()) {
            CreateCountryDto countryDto = new CreateCountryDto(countryCode.getCountryName(), countryCode.getCode());

            try {
                countryService.createCountry(countryDto);
            } catch (Exception e) {
                System.out.println("Country " + countryDto.getName() + " already exists or could not be created: "
                        + e.getMessage());
            }
        }
    }

    private void seedUsers() {
        if (!userService.isUserListEmpty())
            return;

        CreateUserDto createUserDto = new CreateUserDto("travel@log.com", "travelLogAdmin", "test12345");

        try {
            userService.createUser(createUserDto, RoleEnum.ADMIN);
        } catch (Exception e) {
            System.out.println("User " + createUserDto.getEmail() + " already exists or could not be created: "
                    + e.getMessage());
        }
    }
}
