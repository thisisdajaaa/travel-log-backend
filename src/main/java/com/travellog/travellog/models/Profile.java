package com.travellog.travellog.models;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travellog.travellog.constants.GenderEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.*;

@Entity
@Table(name = "profile")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user"})
public class Profile extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name="first_name")
    private String firstName;

    @Column(name= "last_name")
    private String lastName;

    @Column(nullable = false, name = "middle_name")
    private String middleName;

    @Enumerated(EnumType.STRING)
    // @Column(nullable = false)
    private GenderEnum sex;

    @Column()
    private String image;

    @Column(nullable=false, name="address_one")
    private String addressOne;

    @Column(name="address_two")
    private String addressTwo;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false, name="zip_code")
    private Integer zipCode;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "birth_date")
    private Date birthDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

}