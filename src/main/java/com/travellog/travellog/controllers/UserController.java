package com.travellog.travellog.controllers;

import com.travellog.travellog.dtos.CreateUserDto;
import com.travellog.travellog.dtos.UserDetailDto;
import com.travellog.travellog.helpers.ResponseHelper;
import com.travellog.travellog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseHelper<UserDetailDto>> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        return new ResponseEntity<>(
                new ResponseHelper<>(true, "Successfully created user!", userService.createUser(createUserDto)),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<ResponseHelper<List<UserDetailDto>>> getUsers() {
        return new ResponseEntity<>(
                new ResponseHelper<>(true, "Successfully retrieved all users!", userService.getUsers()),
                HttpStatus.OK
        );
    }
}
