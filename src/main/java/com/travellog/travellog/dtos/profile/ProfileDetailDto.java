package com.travellog.travellog.dtos.profile;

import java.time.LocalDate;

import com.travellog.travellog.constants.GenderEnum;

import com.travellog.travellog.dtos.address.AddressDetailDto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDetailDto {
    private Integer id;

    private String firstName;

    private String lastName;

    private String middleName;

    private GenderEnum sex;

    private String profilePhoto;

    private String coverPhoto;

    private LocalDate birthDate;

    private String username;

    private String email;

    private AddressDetailDto addressDetail;

}