package com.travellog.travellog.models;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travellog.travellog.constants.GenderEnum;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "profile")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Transactional
@ToString(exclude = {"user"})
public class Profile extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="first_name")
    private String firstName;

    @Column(name= "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Enumerated(EnumType.STRING)
    private GenderEnum sex;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @Column(name="address_one")
    private String addressOne;

    @Column(name="address_two")
    private String addressTwo;

    @Column(name="state")
    private String state;

    @Column(name="city")
    private String city;

    @Column(name="zip_code")
    private Integer zipcode;

    @Column(name = "cover_photo")
    private String coverPhoto;

    @Column(name = "birth_date")
    private Date birthDate;

    @OneToOne
    @JoinColumn(name="user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @JsonIgnore
    private Country country;

}