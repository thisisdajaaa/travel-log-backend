package com.travellog.travellog.controllers;

import com.travellog.travellog.dtos.CreateUserDto;
import com.travellog.travellog.dtos.UserDetailDto;
import com.travellog.travellog.helpers.ResponseHelper;
import com.travellog.travellog.services.spec.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseHelper.CustomResponse<UserDetailDto>> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully created user!", userService.createUser(createUserDto)),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseHelper.CustomResponse<List<UserDetailDto>>> getUsers() {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved all users!", userService.getUsers()),
                HttpStatus.OK);
    }
}
