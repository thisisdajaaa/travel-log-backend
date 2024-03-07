package com.travellog.travellog.dtos.profile;

import java.sql.Date;
import java.time.LocalDate;

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

    private String firstName;

    private String lastName;

    private String middleName;

    @Enumerated(EnumType.STRING)
    private GenderEnum sex;

    private String image;

    private String addressOne;

    private String addressTwo;

    private String state;

    private String city;

    private Integer zipCode;

    private String profilePhoto;

    private String coverPhoto;

    private Integer countryId;

    @PastOrPresent
    private LocalDate birthDate;

}