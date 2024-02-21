package com.travellog.travellog.dtos.profile;

import java.sql.Date;

import com.travellog.travellog.constants.GenderEnum;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProfileDto{

    @NotEmpty(message = "first name field should not be empty!")
    private String firstName;

    @NotEmpty
    private String lastName;

    private String middleName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GenderEnum sex;

    private String image;

    @NotEmpty
    private String addressOne;

    @NotEmpty
    private String addressTwo;

    @NotNull
    private String state;

    @NotNull
    private Integer zipCode;

    private String coverImage;

    @NotNull
    @PastOrPresent
    private Date birthDate;

}