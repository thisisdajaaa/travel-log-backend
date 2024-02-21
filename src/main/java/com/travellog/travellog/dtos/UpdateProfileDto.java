package com.travellog.travellog.dtos;

import java.sql.Date;

import com.travellog.travellog.constants.GenderEnum;

import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProfileDto {

    private String firstName;

    private String lastName;

    private String middleName;

    private GenderEnum sex;

    private String image;

    private String addressOne;

    private String addressTwo;

    private String state;

    private Integer zipCode;

    private String coverImage;

    @Past
    private Date birthDate;
}