package com.travellog.travellog.dtos.profile;

import java.time.LocalDate;

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

    private String profilePhoto;

    private String addressOne;

    private String addressTwo;

    private String state;

    private String city;

    private Integer zipCode;

    private String coverPhoto;

    private Integer countryId;

    @Past
    private LocalDate birthDate;
}